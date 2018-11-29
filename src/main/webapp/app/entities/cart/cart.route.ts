import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Cart } from 'app/shared/model/cart.model';
import { CartService } from './cart.service';
import { CartComponent } from './cart.component';
import { CartDetailComponent } from './cart-detail.component';
import { CartUpdateComponent } from './cart-update.component';
import { CartDeletePopupComponent } from './cart-delete-dialog.component';
import { ICart } from 'app/shared/model/cart.model';

@Injectable({ providedIn: 'root' })
export class CartResolve implements Resolve<ICart> {
    constructor(private service: CartService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((cart: HttpResponse<Cart>) => cart.body));
        }
        return of(new Cart());
    }
}

export const cartRoute: Routes = [
    {
        path: 'cart',
        component: CartComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'happybourBackEndApp.cart.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'cart/:id/view',
        component: CartDetailComponent,
        resolve: {
            cart: CartResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'happybourBackEndApp.cart.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'cart/new',
        component: CartUpdateComponent,
        resolve: {
            cart: CartResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'happybourBackEndApp.cart.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'cart/:id/edit',
        component: CartUpdateComponent,
        resolve: {
            cart: CartResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'happybourBackEndApp.cart.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const cartPopupRoute: Routes = [
    {
        path: 'cart/:id/delete',
        component: CartDeletePopupComponent,
        resolve: {
            cart: CartResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'happybourBackEndApp.cart.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
