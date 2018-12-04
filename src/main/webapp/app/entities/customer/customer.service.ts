import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICustomer } from 'app/shared/model/customer.model';

type EntityResponseType = HttpResponse<ICustomer>;
type EntityArrayResponseType = HttpResponse<ICustomer[]>;

@Injectable({ providedIn: 'root' })
export class CustomerService {
    private resourceUrl = SERVER_API_URL + 'api/customers';

    constructor(private http: HttpClient) {}

    create(customer: ICustomer): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(customer);
        return this.http
            .post<ICustomer>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(customer: ICustomer): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(customer);
        return this.http
            .put<ICustomer>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ICustomer>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICustomer[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(customer: ICustomer): ICustomer {
        const copy: ICustomer = Object.assign({}, customer, {
            dateCreated: customer.dateCreated != null && customer.dateCreated.isValid() ? customer.dateCreated.toJSON() : null,
            dateUpdated: customer.dateUpdated != null && customer.dateUpdated.isValid() ? customer.dateUpdated.toJSON() : null,
            dateOfBirth: customer.dateOfBirth != null && customer.dateOfBirth.isValid() ? customer.dateOfBirth.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.dateCreated = res.body.dateCreated != null ? moment(res.body.dateCreated) : null;
        res.body.dateUpdated = res.body.dateUpdated != null ? moment(res.body.dateUpdated) : null;
        res.body.dateOfBirth = res.body.dateOfBirth != null ? moment(res.body.dateOfBirth) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((customer: ICustomer) => {
            customer.dateCreated = customer.dateCreated != null ? moment(customer.dateCreated) : null;
            customer.dateUpdated = customer.dateUpdated != null ? moment(customer.dateUpdated) : null;
            customer.dateOfBirth = customer.dateOfBirth != null ? moment(customer.dateOfBirth) : null;
        });
        return res;
    }
}
