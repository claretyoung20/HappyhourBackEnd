import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IStaff } from 'app/shared/model/staff.model';
import { StaffService } from './staff.service';
import { IUser, UserService } from 'app/core';
import { IRestaurant } from 'app/shared/model/restaurant.model';
import { RestaurantService } from 'app/entities/restaurant';

@Component({
    selector: 'jhi-staff-update',
    templateUrl: './staff-update.component.html'
})
export class StaffUpdateComponent implements OnInit {
    private _staff: IStaff;
    isSaving: boolean;

    users: IUser[];

    restaurants: IRestaurant[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private staffService: StaffService,
        private userService: UserService,
        private restaurantService: RestaurantService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ staff }) => {
            this.staff = staff;
        });
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
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
        if (this.staff.id !== undefined) {
            this.subscribeToSaveResponse(this.staffService.update(this.staff));
        } else {
            this.subscribeToSaveResponse(this.staffService.create(this.staff));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IStaff>>) {
        result.subscribe((res: HttpResponse<IStaff>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackUserById(index: number, item: IUser) {
        return item.id;
    }

    trackRestaurantById(index: number, item: IRestaurant) {
        return item.id;
    }
    get staff() {
        return this._staff;
    }

    set staff(staff: IStaff) {
        this._staff = staff;
    }
}
