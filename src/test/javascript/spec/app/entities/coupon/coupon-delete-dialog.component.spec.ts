/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { HappybourBackEndTestModule } from '../../../test.module';
import { CouponDeleteDialogComponent } from 'app/entities/coupon/coupon-delete-dialog.component';
import { CouponService } from 'app/entities/coupon/coupon.service';

describe('Component Tests', () => {
    describe('Coupon Management Delete Component', () => {
        let comp: CouponDeleteDialogComponent;
        let fixture: ComponentFixture<CouponDeleteDialogComponent>;
        let service: CouponService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HappybourBackEndTestModule],
                declarations: [CouponDeleteDialogComponent]
            })
                .overrideTemplate(CouponDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CouponDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CouponService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it(
                'Should call delete service on confirmDelete',
                inject(
                    [],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });
});
