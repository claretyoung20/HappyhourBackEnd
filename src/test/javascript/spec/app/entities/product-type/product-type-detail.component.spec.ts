/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HappybourBackEndTestModule } from '../../../test.module';
import { ProductTypeDetailComponent } from 'app/entities/product-type/product-type-detail.component';
import { ProductType } from 'app/shared/model/product-type.model';

describe('Component Tests', () => {
    describe('ProductType Management Detail Component', () => {
        let comp: ProductTypeDetailComponent;
        let fixture: ComponentFixture<ProductTypeDetailComponent>;
        const route = ({ data: of({ productType: new ProductType(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HappybourBackEndTestModule],
                declarations: [ProductTypeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ProductTypeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProductTypeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.productType).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
