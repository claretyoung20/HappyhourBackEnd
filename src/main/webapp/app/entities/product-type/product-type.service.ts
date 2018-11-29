import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProductType } from 'app/shared/model/product-type.model';

type EntityResponseType = HttpResponse<IProductType>;
type EntityArrayResponseType = HttpResponse<IProductType[]>;

@Injectable({ providedIn: 'root' })
export class ProductTypeService {
    private resourceUrl = SERVER_API_URL + 'api/product-types';

    constructor(private http: HttpClient) {}

    create(productType: IProductType): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(productType);
        return this.http
            .post<IProductType>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(productType: IProductType): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(productType);
        return this.http
            .put<IProductType>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IProductType>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IProductType[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(productType: IProductType): IProductType {
        const copy: IProductType = Object.assign({}, productType, {
            dateCreated: productType.dateCreated != null && productType.dateCreated.isValid() ? productType.dateCreated.toJSON() : null,
            dateUpdated: productType.dateUpdated != null && productType.dateUpdated.isValid() ? productType.dateUpdated.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.dateCreated = res.body.dateCreated != null ? moment(res.body.dateCreated) : null;
        res.body.dateUpdated = res.body.dateUpdated != null ? moment(res.body.dateUpdated) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((productType: IProductType) => {
            productType.dateCreated = productType.dateCreated != null ? moment(productType.dateCreated) : null;
            productType.dateUpdated = productType.dateUpdated != null ? moment(productType.dateUpdated) : null;
        });
        return res;
    }
}
