import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { SaleOrder } from 'app/shared/model/sale-order.model';
import { SaleOrderService } from './sale-order.service';
import { SaleOrderComponent } from './sale-order.component';
import { SaleOrderDetailComponent } from './sale-order-detail.component';
import { SaleOrderUpdateComponent } from './sale-order-update.component';
import { SaleOrderDeletePopupComponent } from './sale-order-delete-dialog.component';
import { ISaleOrder } from 'app/shared/model/sale-order.model';

@Injectable({ providedIn: 'root' })
export class SaleOrderResolve implements Resolve<ISaleOrder> {
    constructor(private service: SaleOrderService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((saleOrder: HttpResponse<SaleOrder>) => saleOrder.body));
        }
        return of(new SaleOrder());
    }
}

export const saleOrderRoute: Routes = [
    {
        path: 'sale-order',
        component: SaleOrderComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'happybourBackEndApp.saleOrder.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'sale-order/:id/view',
        component: SaleOrderDetailComponent,
        resolve: {
            saleOrder: SaleOrderResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'happybourBackEndApp.saleOrder.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'sale-order/new',
        component: SaleOrderUpdateComponent,
        resolve: {
            saleOrder: SaleOrderResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'happybourBackEndApp.saleOrder.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'sale-order/:id/edit',
        component: SaleOrderUpdateComponent,
        resolve: {
            saleOrder: SaleOrderResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'happybourBackEndApp.saleOrder.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const saleOrderPopupRoute: Routes = [
    {
        path: 'sale-order/:id/delete',
        component: SaleOrderDeletePopupComponent,
        resolve: {
            saleOrder: SaleOrderResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'happybourBackEndApp.saleOrder.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
