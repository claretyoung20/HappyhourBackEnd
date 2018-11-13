import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ContactUs } from 'app/shared/model/contact-us.model';
import { ContactUsService } from './contact-us.service';
import { ContactUsComponent } from './contact-us.component';
import { ContactUsDetailComponent } from './contact-us-detail.component';
import { ContactUsUpdateComponent } from './contact-us-update.component';
import { ContactUsDeletePopupComponent } from './contact-us-delete-dialog.component';
import { IContactUs } from 'app/shared/model/contact-us.model';

@Injectable({ providedIn: 'root' })
export class ContactUsResolve implements Resolve<IContactUs> {
    constructor(private service: ContactUsService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((contactUs: HttpResponse<ContactUs>) => contactUs.body));
        }
        return of(new ContactUs());
    }
}

export const contactUsRoute: Routes = [
    {
        path: 'contact-us',
        component: ContactUsComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'happybourBackEndApp.contactUs.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'contact-us/:id/view',
        component: ContactUsDetailComponent,
        resolve: {
            contactUs: ContactUsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'happybourBackEndApp.contactUs.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'contact-us/new',
        component: ContactUsUpdateComponent,
        resolve: {
            contactUs: ContactUsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'happybourBackEndApp.contactUs.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'contact-us/:id/edit',
        component: ContactUsUpdateComponent,
        resolve: {
            contactUs: ContactUsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'happybourBackEndApp.contactUs.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const contactUsPopupRoute: Routes = [
    {
        path: 'contact-us/:id/delete',
        component: ContactUsDeletePopupComponent,
        resolve: {
            contactUs: ContactUsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'happybourBackEndApp.contactUs.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
