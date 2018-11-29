/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { HappybourBackEndTestModule } from '../../../test.module';
import { ProductImageUpdateComponent } from 'app/entities/product-image/product-image-update.component';
import { ProductImageService } from 'app/entities/product-image/product-image.service';
import { ProductImage } from 'app/shared/model/product-image.model';

describe('Component Tests', () => {
    describe('ProductImage Management Update Component', () => {
        let comp: ProductImageUpdateComponent;
        let fixture: ComponentFixture<ProductImageUpdateComponent>;
        let service: ProductImageService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HappybourBackEndTestModule],
                declarations: [ProductImageUpdateComponent]
            })
                .overrideTemplate(ProductImageUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ProductImageUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductImageService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ProductImage(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.productImage = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ProductImage();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.productImage = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
