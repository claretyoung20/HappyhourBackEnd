import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { ICoupon } from 'app/shared/model/coupon.model';
import { CouponService } from './coupon.service';
import { IRestaurant } from 'app/shared/model/restaurant.model';
import { RestaurantService } from 'app/entities/restaurant';

@Component({
    selector: 'jhi-coupon-update',
    templateUrl: './coupon-update.component.html'
})
export class CouponUpdateComponent implements OnInit {
    private _coupon: ICoupon;
    isSaving: boolean;

    restaurants: IRestaurant[];
    dateCreated: string;
    dateUpdated: string;
    endDateDp: any;
    startFromDateDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private couponService: CouponService,
        private restaurantService: RestaurantService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ coupon }) => {
            this.coupon = coupon;
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
        this.coupon.dateCreated = moment(this.dateCreated, DATE_TIME_FORMAT);
        this.coupon.dateUpdated = moment(this.dateUpdated, DATE_TIME_FORMAT);
        if (this.coupon.id !== undefined) {
            this.subscribeToSaveResponse(this.couponService.update(this.coupon));
        } else {
            this.subscribeToSaveResponse(this.couponService.create(this.coupon));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICoupon>>) {
        result.subscribe((res: HttpResponse<ICoupon>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
    get coupon() {
        return this._coupon;
    }

    set coupon(coupon: ICoupon) {
        this._coupon = coupon;
        this.dateCreated = moment(coupon.dateCreated).format(DATE_TIME_FORMAT);
        this.dateUpdated = moment(coupon.dateUpdated).format(DATE_TIME_FORMAT);
    }
}
