import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ITimeManager } from 'app/shared/model/time-manager.model';
import { TimeManagerService } from './time-manager.service';
import { IRestaurant } from 'app/shared/model/restaurant.model';
import { RestaurantService } from 'app/entities/restaurant';

@Component({
    selector: 'jhi-time-manager-update',
    templateUrl: './time-manager-update.component.html'
})
export class TimeManagerUpdateComponent implements OnInit {
    private _timeManager: ITimeManager;
    isSaving: boolean;

    restaurants: IRestaurant[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private timeManagerService: TimeManagerService,
        private restaurantService: RestaurantService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ timeManager }) => {
            this.timeManager = timeManager;
        });
        this.restaurantService.query().subscribe(
            (res: HttpResponse<IRestaurant[]>) => {
                this.restaurants = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.timeManager.id !== undefined) {
            this.subscribeToSaveResponse(this.timeManagerService.update(this.timeManager));
        } else {
            this.subscribeToSaveResponse(this.timeManagerService.create(this.timeManager));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ITimeManager>>) {
        result.subscribe((res: HttpResponse<ITimeManager>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackRestaurantById(index: number, item: IRestaurant) {
        return item.id;
    }
    get timeManager() {
        return this._timeManager;
    }

    set timeManager(timeManager: ITimeManager) {
        this._timeManager = timeManager;
    }
}
