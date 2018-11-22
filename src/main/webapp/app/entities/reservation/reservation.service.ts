import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IReservation } from 'app/shared/model/reservation.model';

type EntityResponseType = HttpResponse<IReservation>;
type EntityArrayResponseType = HttpResponse<IReservation[]>;

@Injectable({ providedIn: 'root' })
export class ReservationService {
    private resourceUrl = SERVER_API_URL + 'api/reservations';

    constructor(private http: HttpClient) {}

    create(reservation: IReservation): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(reservation);
        return this.http
            .post<IReservation>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(reservation: IReservation): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(reservation);
        return this.http
            .put<IReservation>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IReservation>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IReservation[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(reservation: IReservation): IReservation {
        const copy: IReservation = Object.assign({}, reservation, {
            reserverDate:
                reservation.reserverDate != null && reservation.reserverDate.isValid()
                    ? reservation.reserverDate.format(DATE_FORMAT)
                    : null,
            updatedDate:
                reservation.updatedDate != null && reservation.updatedDate.isValid() ? reservation.updatedDate.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.reserverDate = res.body.reserverDate != null ? moment(res.body.reserverDate) : null;
        res.body.updatedDate = res.body.updatedDate != null ? moment(res.body.updatedDate) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((reservation: IReservation) => {
            reservation.reserverDate = reservation.reserverDate != null ? moment(reservation.reserverDate) : null;
            reservation.updatedDate = reservation.updatedDate != null ? moment(reservation.updatedDate) : null;
        });
        return res;
    }
}
