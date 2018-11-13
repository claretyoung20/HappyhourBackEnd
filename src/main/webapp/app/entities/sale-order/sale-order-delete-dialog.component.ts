import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISaleOrder } from 'app/shared/model/sale-order.model';
import { SaleOrderService } from './sale-order.service';

@Component({
    selector: 'jhi-sale-order-delete-dialog',
    templateUrl: './sale-order-delete-dialog.component.html'
})
export class SaleOrderDeleteDialogComponent {
    saleOrder: ISaleOrder;

    constructor(private saleOrderService: SaleOrderService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.saleOrderService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'saleOrderListModification',
                content: 'Deleted an saleOrder'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-sale-order-delete-popup',
    template: ''
})
export class SaleOrderDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ saleOrder }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SaleOrderDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.saleOrder = saleOrder;
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
