import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICoupon } from 'app/shared/model/coupon.model';

type EntityResponseType = HttpResponse<ICoupon>;
type EntityArrayResponseType = HttpResponse<ICoupon[]>;

@Injectable({ providedIn: 'root' })
export class CouponService {
    private resourceUrl = SERVER_API_URL + 'api/coupons';

    constructor(private http: HttpClient) {}

    create(coupon: ICoupon): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(coupon);
        return this.http
            .post<ICoupon>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(coupon: ICoupon): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(coupon);
        return this.http
            .put<ICoupon>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ICoupon>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICoupon[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(coupon: ICoupon): ICoupon {
        const copy: ICoupon = Object.assign({}, coupon, {
            dateCreated: coupon.dateCreated != null && coupon.dateCreated.isValid() ? coupon.dateCreated.toJSON() : null,
            dateUpdated: coupon.dateUpdated != null && coupon.dateUpdated.isValid() ? coupon.dateUpdated.toJSON() : null,
            endDate: coupon.endDate != null && coupon.endDate.isValid() ? coupon.endDate.format(DATE_FORMAT) : null,
            startFromDate: coupon.startFromDate != null && coupon.startFromDate.isValid() ? coupon.startFromDate.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.dateCreated = res.body.dateCreated != null ? moment(res.body.dateCreated) : null;
        res.body.dateUpdated = res.body.dateUpdated != null ? moment(res.body.dateUpdated) : null;
        res.body.endDate = res.body.endDate != null ? moment(res.body.endDate) : null;
        res.body.startFromDate = res.body.startFromDate != null ? moment(res.body.startFromDate) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((coupon: ICoupon) => {
            coupon.dateCreated = coupon.dateCreated != null ? moment(coupon.dateCreated) : null;
            coupon.dateUpdated = coupon.dateUpdated != null ? moment(coupon.dateUpdated) : null;
            coupon.endDate = coupon.endDate != null ? moment(coupon.endDate) : null;
            coupon.startFromDate = coupon.startFromDate != null ? moment(coupon.startFromDate) : null;
        });
        return res;
    }
}
