/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { HappybourBackEndTestModule } from '../../../test.module';
import { ProductImageDeleteDialogComponent } from 'app/entities/product-image/product-image-delete-dialog.component';
import { ProductImageService } from 'app/entities/product-image/product-image.service';

describe('Component Tests', () => {
    describe('ProductImage Management Delete Component', () => {
        let comp: ProductImageDeleteDialogComponent;
        let fixture: ComponentFixture<ProductImageDeleteDialogComponent>;
        let service: ProductImageService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HappybourBackEndTestModule],
                declarations: [ProductImageDeleteDialogComponent]
            })
                .overrideTemplate(ProductImageDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProductImageDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductImageService);
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
