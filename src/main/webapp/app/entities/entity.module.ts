import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { HappybourBackEndRestaurantModule } from './restaurant/restaurant.module';
import { HappybourBackEndCustomerModule } from './customer/customer.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        HappybourBackEndRestaurantModule,
        HappybourBackEndCustomerModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HappybourBackEndEntityModule {}
