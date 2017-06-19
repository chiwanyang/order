import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { UserMenuRel } from './user-menu-rel.model';
import { UserMenuRelService } from './user-menu-rel.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-user-menu-rel',
    templateUrl: './user-menu-rel.component.html'
})
export class UserMenuRelComponent implements OnInit, OnDestroy {
userMenuRels: UserMenuRel[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private userMenuRelService: UserMenuRelService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.userMenuRelService.query().subscribe(
            (res: ResponseWrapper) => {
                this.userMenuRels = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInUserMenuRels();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: UserMenuRel) {
        return item.id;
    }
    registerChangeInUserMenuRels() {
        this.eventSubscriber = this.eventManager.subscribe('userMenuRelListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
