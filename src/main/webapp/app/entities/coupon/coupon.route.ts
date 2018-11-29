import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Coupon } from 'app/shared/model/coupon.model';
import { CouponService } from './coupon.service';
import { CouponComponent } from './coupon.component';
import { CouponDetailComponent } from './coupon-detail.component';
import { CouponUpdateComponent } from './coupon-update.component';
import { CouponDeletePopupComponent } from './coupon-delete-dialog.component';
import { ICoupon } from 'app/shared/model/coupon.model';

@Injectable({ providedIn: 'root' })
export class CouponResolve implements Resolve<ICoupon> {
    constructor(private service: CouponService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((coupon: HttpResponse<Coupon>) => coupon.body));
        }
        return of(new Coupon());
    }
}

export const couponRoute: Routes = [
    {
        path: 'coupon',
        component: CouponComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'happybourBackEndApp.coupon.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'coupon/:id/view',
        component: CouponDetailComponent,
        resolve: {
            coupon: CouponResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'happybourBackEndApp.coupon.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'coupon/new',
        component: CouponUpdateComponent,
        resolve: {
            coupon: CouponResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'happybourBackEndApp.coupon.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'coupon/:id/edit',
        component: CouponUpdateComponent,
        resolve: {
            coupon: CouponResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'happybourBackEndApp.coupon.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const couponPopupRoute: Routes = [
    {
        path: 'coupon/:id/delete',
        component: CouponDeletePopupComponent,
        resolve: {
            coupon: CouponResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'happybourBackEndApp.coupon.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
