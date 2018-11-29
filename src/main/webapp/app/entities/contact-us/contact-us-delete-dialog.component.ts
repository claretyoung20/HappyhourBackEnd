import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IContactUs } from 'app/shared/model/contact-us.model';
import { ContactUsService } from './contact-us.service';

@Component({
    selector: 'jhi-contact-us-delete-dialog',
    templateUrl: './contact-us-delete-dialog.component.html'
})
export class ContactUsDeleteDialogComponent {
    contactUs: IContactUs;

    constructor(private contactUsService: ContactUsService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.contactUsService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'contactUsListModification',
                content: 'Deleted an contactUs'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-contact-us-delete-popup',
    template: ''
})
export class ContactUsDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ contactUs }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ContactUsDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.contactUs = contactUs;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
