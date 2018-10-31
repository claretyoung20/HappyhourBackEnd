import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IRestaurant } from 'app/shared/model/restaurant.model';
import { RestaurantService } from './restaurant.service';

@Component({
    selector: 'jhi-restaurant-update',
    templateUrl: './restaurant-update.component.html'
})
export class RestaurantUpdateComponent implements OnInit {
    private _restaurant: IRestaurant;
    isSaving: boolean;

    constructor(private restaurantService: RestaurantService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ restaurant }) => {
            this.restaurant = restaurant;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.restaurant.id !== undefined) {
            this.subscribeToSaveResponse(this.restaurantService.update(this.restaurant));
        } else {
            this.subscribeToSaveResponse(this.restaurantService.create(this.restaurant));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IRestaurant>>) {
        result.subscribe((res: HttpResponse<IRestaurant>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get restaurant() {
        return this._restaurant;
    }

    set restaurant(restaurant: IRestaurant) {
        this._restaurant = restaurant;
    }
}
