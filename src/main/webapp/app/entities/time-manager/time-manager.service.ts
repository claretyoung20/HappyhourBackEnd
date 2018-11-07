import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITimeManager } from 'app/shared/model/time-manager.model';

type EntityResponseType = HttpResponse<ITimeManager>;
type EntityArrayResponseType = HttpResponse<ITimeManager[]>;

@Injectable({ providedIn: 'root' })
export class TimeManagerService {
    private resourceUrl = SERVER_API_URL + 'api/time-managers';

    constructor(private http: HttpClient) {}

    create(timeManager: ITimeManager): Observable<EntityResponseType> {
        return this.http.post<ITimeManager>(this.resourceUrl, timeManager, { observe: 'response' });
    }

    update(timeManager: ITimeManager): Observable<EntityResponseType> {
        return this.http.put<ITimeManager>(this.resourceUrl, timeManager, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITimeManager>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITimeManager[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
