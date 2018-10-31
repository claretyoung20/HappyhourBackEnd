import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRestaurant } from 'app/shared/model/restaurant.model';

type EntityResponseType = HttpResponse<IRestaurant>;
type EntityArrayResponseType = HttpResponse<IRestaurant[]>;

@Injectable({ providedIn: 'root' })
export class RestaurantService {
    private resourceUrl = SERVER_API_URL + 'api/restaurants';

    constructor(private http: HttpClient) {}

    create(restaurant: IRestaurant): Observable<EntityResponseType> {
        return this.http.post<IRestaurant>(this.resourceUrl, restaurant, { observe: 'response' });
    }

    update(restaurant: IRestaurant): Observable<EntityResponseType> {
        return this.http.put<IRestaurant>(this.resourceUrl, restaurant, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IRestaurant>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IRestaurant[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
