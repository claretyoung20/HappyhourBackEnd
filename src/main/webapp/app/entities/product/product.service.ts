import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProduct } from 'app/shared/model/product.model';

type EntityResponseType = HttpResponse<IProduct>;
type EntityArrayResponseType = HttpResponse<IProduct[]>;

@Injectable({ providedIn: 'root' })
export class ProductService {
    private resourceUrl = SERVER_API_URL + 'api/products';

    constructor(private http: HttpClient) {}

    create(product: IProduct): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(product);
        return this.http
            .post<IProduct>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(product: IProduct): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(product);
        return this.http
            .put<IProduct>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IProduct>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IProduct[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(product: IProduct): IProduct {
        const copy: IProduct = Object.assign({}, product, {
            createdDate: product.createdDate != null && product.createdDate.isValid() ? product.createdDate.toJSON() : null,
            updatedDate: product.updatedDate != null && product.updatedDate.isValid() ? product.updatedDate.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.createdDate = res.body.createdDate != null ? moment(res.body.createdDate) : null;
        res.body.updatedDate = res.body.updatedDate != null ? moment(res.body.updatedDate) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((product: IProduct) => {
            product.createdDate = product.createdDate != null ? moment(product.createdDate) : null;
            product.updatedDate = product.updatedDate != null ? moment(product.updatedDate) : null;
        });
        return res;
    }
}
