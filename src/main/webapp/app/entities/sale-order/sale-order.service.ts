import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISaleOrder } from 'app/shared/model/sale-order.model';

type EntityResponseType = HttpResponse<ISaleOrder>;
type EntityArrayResponseType = HttpResponse<ISaleOrder[]>;

@Injectable({ providedIn: 'root' })
export class SaleOrderService {
    private resourceUrl = SERVER_API_URL + 'api/sale-orders';

    constructor(private http: HttpClient) {}

    create(saleOrder: ISaleOrder): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(saleOrder);
        return this.http
            .post<ISaleOrder>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(saleOrder: ISaleOrder): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(saleOrder);
        return this.http
            .put<ISaleOrder>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ISaleOrder>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISaleOrder[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(saleOrder: ISaleOrder): ISaleOrder {
        const copy: ISaleOrder = Object.assign({}, saleOrder, {
            dateCreated: saleOrder.dateCreated != null && saleOrder.dateCreated.isValid() ? saleOrder.dateCreated.toJSON() : null,
            dateUpdated: saleOrder.dateUpdated != null && saleOrder.dateUpdated.isValid() ? saleOrder.dateUpdated.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.dateCreated = res.body.dateCreated != null ? moment(res.body.dateCreated) : null;
        res.body.dateUpdated = res.body.dateUpdated != null ? moment(res.body.dateUpdated) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((saleOrder: ISaleOrder) => {
            saleOrder.dateCreated = saleOrder.dateCreated != null ? moment(saleOrder.dateCreated) : null;
            saleOrder.dateUpdated = saleOrder.dateUpdated != null ? moment(saleOrder.dateUpdated) : null;
        });
        return res;
    }
}
