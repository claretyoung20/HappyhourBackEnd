import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IHappyOrder } from 'app/shared/model/happy-order.model';
import { HappyOrderService } from './happy-order.service';

@Component({
    selector: 'jhi-happy-order-delete-dialog',
    templateUrl: './happy-order-delete-dialog.component.html'
})
export class HappyOrderDeleteDialogComponent {
    happyOrder: IHappyOrder;

    constructor(private happyOrderService: HappyOrderService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.happyOrderService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'happyOrderListModification',
                content: 'Deleted an happyOrder'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-happy-order-delete-popup',
    template: ''
})
export class HappyOrderDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ happyOrder }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(HappyOrderDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.happyOrder = happyOrder;
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
