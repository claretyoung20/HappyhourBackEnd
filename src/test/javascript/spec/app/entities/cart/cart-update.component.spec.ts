/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { HappybourBackEndTestModule } from '../../../test.module';
import { CartUpdateComponent } from 'app/entities/cart/cart-update.component';
import { CartService } from 'app/entities/cart/cart.service';
import { Cart } from 'app/shared/model/cart.model';

describe('Component Tests', () => {
    describe('Cart Management Update Component', () => {
        let comp: CartUpdateComponent;
        let fixture: ComponentFixture<CartUpdateComponent>;
        let service: CartService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HappybourBackEndTestModule],
                declarations: [CartUpdateComponent]
            })
                .overrideTemplate(CartUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CartUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CartService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Cart(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.cart = entity;
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
                    const entity = new Cart();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.cart = entity;
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
