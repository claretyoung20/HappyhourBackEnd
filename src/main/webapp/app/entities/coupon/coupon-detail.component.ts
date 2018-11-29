import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICoupon } from 'app/shared/model/coupon.model';

@Component({
    selector: 'jhi-coupon-detail',
    templateUrl: './coupon-detail.component.html'
})
export class CouponDetailComponent implements OnInit {
    coupon: ICoupon;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ coupon }) => {
            this.coupon = coupon;
        });
    }

    previousState() {
        window.history.back();
    }
}
