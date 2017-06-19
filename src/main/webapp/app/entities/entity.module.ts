import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { OrderMenuModule } from './menu/menu.module';
import { OrderUserMenuRelModule } from './user-menu-rel/user-menu-rel.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        OrderMenuModule,
        OrderUserMenuRelModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OrderEntityModule {}
