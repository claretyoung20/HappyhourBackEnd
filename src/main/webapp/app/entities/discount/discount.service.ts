import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDiscount } from 'app/shared/model/discount.model';

type EntityResponseType = HttpResponse<IDiscount>;
type EntityArrayResponseType = HttpResponse<IDiscount[]>;

@Injectable({ providedIn: 'root' })
export class DiscountService {
    private resourceUrl = SERVER_API_URL + 'api/discounts';

    constructor(private http: HttpClient) {}

    create(discount: IDiscount): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(discount);
        return this.http
            .post<IDiscount>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(discount: IDiscount): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(discount);
        return this.http
            .put<IDiscount>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IDiscount>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IDiscount[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(discount: IDiscount): IDiscount {
        const copy: IDiscount = Object.assign({}, discount, {
            endDate: discount.endDate != null && discount.endDate.isValid() ? discount.endDate.format(DATE_FORMAT) : null,
            startDate: discount.startDate != null && discount.startDate.isValid() ? discount.startDate.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.endDate = res.body.endDate != null ? moment(res.body.endDate) : null;
        res.body.startDate = res.body.startDate != null ? moment(res.body.startDate) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((discount: IDiscount) => {
            discount.endDate = discount.endDate != null ? moment(discount.endDate) : null;
            discount.startDate = discount.startDate != null ? moment(discount.startDate) : null;
        });
        return res;
    }
}
