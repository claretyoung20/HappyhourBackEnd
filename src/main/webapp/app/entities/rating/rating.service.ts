import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRating } from 'app/shared/model/rating.model';

type EntityResponseType = HttpResponse<IRating>;
type EntityArrayResponseType = HttpResponse<IRating[]>;

@Injectable({ providedIn: 'root' })
export class RatingService {
    private resourceUrl = SERVER_API_URL + 'api/ratings';

    constructor(private http: HttpClient) {}

    create(rating: IRating): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(rating);
        return this.http
            .post<IRating>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(rating: IRating): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(rating);
        return this.http
            .put<IRating>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IRating>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IRating[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(rating: IRating): IRating {
        const copy: IRating = Object.assign({}, rating, {
            dateCreated: rating.dateCreated != null && rating.dateCreated.isValid() ? rating.dateCreated.toJSON() : null,
            dateUpdated: rating.dateUpdated != null && rating.dateUpdated.isValid() ? rating.dateUpdated.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.dateCreated = res.body.dateCreated != null ? moment(res.body.dateCreated) : null;
        res.body.dateUpdated = res.body.dateUpdated != null ? moment(res.body.dateUpdated) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((rating: IRating) => {
            rating.dateCreated = rating.dateCreated != null ? moment(rating.dateCreated) : null;
            rating.dateUpdated = rating.dateUpdated != null ? moment(rating.dateUpdated) : null;
        });
        return res;
    }
}
