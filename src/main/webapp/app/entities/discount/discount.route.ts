import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Discount } from 'app/shared/model/discount.model';
import { DiscountService } from './discount.service';
import { DiscountComponent } from './discount.component';
import { DiscountDetailComponent } from './discount-detail.component';
import { DiscountUpdateComponent } from './discount-update.component';
import { DiscountDeletePopupComponent } from './discount-delete-dialog.component';
import { IDiscount } from 'app/shared/model/discount.model';

@Injectable({ providedIn: 'root' })
export class DiscountResolve implements Resolve<IDiscount> {
    constructor(private service: DiscountService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((discount: HttpResponse<Discount>) => discount.body));
        }
        return of(new Discount());
    }
}

export const discountRoute: Routes = [
    {
        path: 'discount',
        component: DiscountComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'happybourBackEndApp.discount.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'discount/:id/view',
        component: DiscountDetailComponent,
        resolve: {
            discount: DiscountResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'happybourBackEndApp.discount.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'discount/new',
        component: DiscountUpdateComponent,
        resolve: {
            discount: DiscountResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'happybourBackEndApp.discount.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'discount/:id/edit',
        component: DiscountUpdateComponent,
        resolve: {
            discount: DiscountResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'happybourBackEndApp.discount.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const discountPopupRoute: Routes = [
    {
        path: 'discount/:id/delete',
        component: DiscountDeletePopupComponent,
        resolve: {
            discount: DiscountResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'happybourBackEndApp.discount.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
