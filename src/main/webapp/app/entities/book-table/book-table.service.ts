import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBookTable } from 'app/shared/model/book-table.model';

type EntityResponseType = HttpResponse<IBookTable>;
type EntityArrayResponseType = HttpResponse<IBookTable[]>;

@Injectable({ providedIn: 'root' })
export class BookTableService {
    private resourceUrl = SERVER_API_URL + 'api/book-tables';

    constructor(private http: HttpClient) {}

    create(bookTable: IBookTable): Observable<EntityResponseType> {
        return this.http.post<IBookTable>(this.resourceUrl, bookTable, { observe: 'response' });
    }

    update(bookTable: IBookTable): Observable<EntityResponseType> {
        return this.http.put<IBookTable>(this.resourceUrl, bookTable, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IBookTable>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IBookTable[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
