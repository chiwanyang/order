import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { UserMenuRel } from './user-menu-rel.model';
import { UserMenuRelService } from './user-menu-rel.service';

@Component({
    selector: 'jhi-user-menu-rel-detail',
    templateUrl: './user-menu-rel-detail.component.html'
})
export class UserMenuRelDetailComponent implements OnInit, OnDestroy {

    userMenuRel: UserMenuRel;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private userMenuRelService: UserMenuRelService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInUserMenuRels();
    }

    load(id) {
        this.userMenuRelService.find(id).subscribe((userMenuRel) => {
            this.userMenuRel = userMenuRel;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInUserMenuRels() {
        this.eventSubscriber = this.eventManager.subscribe(
            'userMenuRelListModification',
            (response) => this.load(this.userMenuRel.id)
        );
    }
}
