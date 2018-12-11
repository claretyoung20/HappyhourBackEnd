import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITableType } from 'app/shared/model/table-type.model';

type EntityResponseType = HttpResponse<ITableType>;
type EntityArrayResponseType = HttpResponse<ITableType[]>;

@Injectable({ providedIn: 'root' })
export class TableTypeService {
    private resourceUrl = SERVER_API_URL + 'api/table-types';

    constructor(private http: HttpClient) {}

    create(tableType: ITableType): Observable<EntityResponseType> {
        return this.http.post<ITableType>(this.resourceUrl, tableType, { observe: 'response' });
    }

    update(tableType: ITableType): Observable<EntityResponseType> {
        return this.http.put<ITableType>(this.resourceUrl, tableType, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITableType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITableType[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
