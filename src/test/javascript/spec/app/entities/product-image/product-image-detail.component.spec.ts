/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HappybourBackEndTestModule } from '../../../test.module';
import { ProductImageDetailComponent } from 'app/entities/product-image/product-image-detail.component';
import { ProductImage } from 'app/shared/model/product-image.model';

describe('Component Tests', () => {
    describe('ProductImage Management Detail Component', () => {
        let comp: ProductImageDetailComponent;
        let fixture: ComponentFixture<ProductImageDetailComponent>;
        const route = ({ data: of({ productImage: new ProductImage(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HappybourBackEndTestModule],
                declarations: [ProductImageDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ProductImageDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProductImageDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.productImage).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
