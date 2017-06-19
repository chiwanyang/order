import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { DateUtils } from 'ng-jhipster';

import { UserMenuRel } from './user-menu-rel.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class UserMenuRelService {

    private resourceUrl = 'api/user-menu-rels';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(userMenuRel: UserMenuRel): Observable<UserMenuRel> {
        const copy = this.convert(userMenuRel);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(userMenuRel: UserMenuRel): Observable<UserMenuRel> {
        const copy = this.convert(userMenuRel);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<UserMenuRel> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.gmtCreate = this.dateUtils
            .convertDateTimeFromServer(entity.gmtCreate);
        entity.gmtModified = this.dateUtils
            .convertDateTimeFromServer(entity.gmtModified);
    }

    private convert(userMenuRel: UserMenuRel): UserMenuRel {
        const copy: UserMenuRel = Object.assign({}, userMenuRel);

        copy.gmtCreate = this.dateUtils.toDate(userMenuRel.gmtCreate);

        copy.gmtModified = this.dateUtils.toDate(userMenuRel.gmtModified);
        return copy;
    }
}
