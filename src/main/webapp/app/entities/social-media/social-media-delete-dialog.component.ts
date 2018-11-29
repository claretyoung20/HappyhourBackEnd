import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISocialMedia } from 'app/shared/model/social-media.model';
import { SocialMediaService } from './social-media.service';

@Component({
    selector: 'jhi-social-media-delete-dialog',
    templateUrl: './social-media-delete-dialog.component.html'
})
export class SocialMediaDeleteDialogComponent {
    socialMedia: ISocialMedia;

    constructor(
        private socialMediaService: SocialMediaService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.socialMediaService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'socialMediaListModification',
                content: 'Deleted an socialMedia'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-social-media-delete-popup',
    template: ''
})
export class SocialMediaDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ socialMedia }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SocialMediaDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.socialMedia = socialMedia;
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
