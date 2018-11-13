import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { HappyOrder } from 'app/shared/model/happy-order.model';
import { HappyOrderService } from './happy-order.service';
import { HappyOrderComponent } from './happy-order.component';
import { HappyOrderDetailComponent } from './happy-order-detail.component';
import { HappyOrderUpdateComponent } from './happy-order-update.component';
import { HappyOrderDeletePopupComponent } from './happy-order-delete-dialog.component';
import { IHappyOrder } from 'app/shared/model/happy-order.model';

@Injectable({ providedIn: 'root' })
export class HappyOrderResolve implements Resolve<IHappyOrder> {
    constructor(private service: HappyOrderService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((happyOrder: HttpResponse<HappyOrder>) => happyOrder.body));
        }
        return of(new HappyOrder());
    }
}

export const happyOrderRoute: Routes = [
    {
        path: 'happy-order',
        component: HappyOrderComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'happybourBackEndApp.happyOrder.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'happy-order/:id/view',
        component: HappyOrderDetailComponent,
        resolve: {
            happyOrder: HappyOrderResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'happybourBackEndApp.happyOrder.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'happy-order/new',
        component: HappyOrderUpdateComponent,
        resolve: {
            happyOrder: HappyOrderResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'happybourBackEndApp.happyOrder.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'happy-order/:id/edit',
        component: HappyOrderUpdateComponent,
        resolve: {
            happyOrder: HappyOrderResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'happybourBackEndApp.happyOrder.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const happyOrderPopupRoute: Routes = [
    {
        path: 'happy-order/:id/delete',
        component: HappyOrderDeletePopupComponent,
        resolve: {
            happyOrder: HappyOrderResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'happybourBackEndApp.happyOrder.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
