import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IHappyOrder } from 'app/shared/model/happy-order.model';

type EntityResponseType = HttpResponse<IHappyOrder>;
type EntityArrayResponseType = HttpResponse<IHappyOrder[]>;

@Injectable({ providedIn: 'root' })
export class HappyOrderService {
    private resourceUrl = SERVER_API_URL + 'api/happy-orders';

    constructor(private http: HttpClient) {}

    create(happyOrder: IHappyOrder): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(happyOrder);
        return this.http
            .post<IHappyOrder>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(happyOrder: IHappyOrder): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(happyOrder);
        return this.http
            .put<IHappyOrder>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IHappyOrder>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IHappyOrder[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(happyOrder: IHappyOrder): IHappyOrder {
        const copy: IHappyOrder = Object.assign({}, happyOrder, {
            dateUpdated:
                happyOrder.dateUpdated != null && happyOrder.dateUpdated.isValid() ? happyOrder.dateUpdated.format(DATE_FORMAT) : null,
            dateCreated:
                happyOrder.dateCreated != null && happyOrder.dateCreated.isValid() ? happyOrder.dateCreated.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.dateUpdated = res.body.dateUpdated != null ? moment(res.body.dateUpdated) : null;
        res.body.dateCreated = res.body.dateCreated != null ? moment(res.body.dateCreated) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((happyOrder: IHappyOrder) => {
            happyOrder.dateUpdated = happyOrder.dateUpdated != null ? moment(happyOrder.dateUpdated) : null;
            happyOrder.dateCreated = happyOrder.dateCreated != null ? moment(happyOrder.dateCreated) : null;
        });
        return res;
    }
}
