import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IContactUs } from 'app/shared/model/contact-us.model';

type EntityResponseType = HttpResponse<IContactUs>;
type EntityArrayResponseType = HttpResponse<IContactUs[]>;

@Injectable({ providedIn: 'root' })
export class ContactUsService {
    private resourceUrl = SERVER_API_URL + 'api/contactuses';

    constructor(private http: HttpClient) {}

    create(contactUs: IContactUs): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(contactUs);
        return this.http
            .post<IContactUs>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(contactUs: IContactUs): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(contactUs);
        return this.http
            .put<IContactUs>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IContactUs>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IContactUs[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(contactUs: IContactUs): IContactUs {
        const copy: IContactUs = Object.assign({}, contactUs, {
            dateCreated: contactUs.dateCreated != null && contactUs.dateCreated.isValid() ? contactUs.dateCreated.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.dateCreated = res.body.dateCreated != null ? moment(res.body.dateCreated) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((contactUs: IContactUs) => {
            contactUs.dateCreated = contactUs.dateCreated != null ? moment(contactUs.dateCreated) : null;
        });
        return res;
    }
}
