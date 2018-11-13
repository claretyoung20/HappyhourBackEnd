import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProductImage } from 'app/shared/model/product-image.model';

type EntityResponseType = HttpResponse<IProductImage>;
type EntityArrayResponseType = HttpResponse<IProductImage[]>;

@Injectable({ providedIn: 'root' })
export class ProductImageService {
    private resourceUrl = SERVER_API_URL + 'api/product-images';

    constructor(private http: HttpClient) {}

    create(productImage: IProductImage): Observable<EntityResponseType> {
        return this.http.post<IProductImage>(this.resourceUrl, productImage, { observe: 'response' });
    }

    update(productImage: IProductImage): Observable<EntityResponseType> {
        return this.http.put<IProductImage>(this.resourceUrl, productImage, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IProductImage>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IProductImage[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
