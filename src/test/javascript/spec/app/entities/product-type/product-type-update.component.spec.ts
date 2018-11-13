/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { HappybourBackEndTestModule } from '../../../test.module';
import { ProductTypeUpdateComponent } from 'app/entities/product-type/product-type-update.component';
import { ProductTypeService } from 'app/entities/product-type/product-type.service';
import { ProductType } from 'app/shared/model/product-type.model';

describe('Component Tests', () => {
    describe('ProductType Management Update Component', () => {
        let comp: ProductTypeUpdateComponent;
        let fixture: ComponentFixture<ProductTypeUpdateComponent>;
        let service: ProductTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HappybourBackEndTestModule],
                declarations: [ProductTypeUpdateComponent]
            })
                .overrideTemplate(ProductTypeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ProductTypeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductTypeService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ProductType(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.productType = entity;
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
                    const entity = new ProductType();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.productType = entity;
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
