/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { HappybourBackEndTestModule } from '../../../test.module';
import { CouponUpdateComponent } from 'app/entities/coupon/coupon-update.component';
import { CouponService } from 'app/entities/coupon/coupon.service';
import { Coupon } from 'app/shared/model/coupon.model';

describe('Component Tests', () => {
    describe('Coupon Management Update Component', () => {
        let comp: CouponUpdateComponent;
        let fixture: ComponentFixture<CouponUpdateComponent>;
        let service: CouponService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HappybourBackEndTestModule],
                declarations: [CouponUpdateComponent]
            })
                .overrideTemplate(CouponUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CouponUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CouponService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Coupon(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.coupon = entity;
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
                    const entity = new Coupon();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.coupon = entity;
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
