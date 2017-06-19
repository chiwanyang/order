import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { UserMenuRel } from './user-menu-rel.model';
import { UserMenuRelService } from './user-menu-rel.service';
@Injectable()
export class UserMenuRelPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private userMenuRelService: UserMenuRelService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.userMenuRelService.find(id).subscribe((userMenuRel) => {
                userMenuRel.gmtCreate = this.datePipe
                    .transform(userMenuRel.gmtCreate, 'yyyy-MM-ddThh:mm');
                userMenuRel.gmtModified = this.datePipe
                    .transform(userMenuRel.gmtModified, 'yyyy-MM-ddThh:mm');
                this.userMenuRelModalRef(component, userMenuRel);
            });
        } else {
            return this.userMenuRelModalRef(component, new UserMenuRel());
        }
    }

    userMenuRelModalRef(component: Component, userMenuRel: UserMenuRel): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.userMenuRel = userMenuRel;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
