"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
const core_1 = require("@angular/core");
const router_1 = require("@angular/router");
const content_service_1 = require("./content.service");
core_1.enableProdMode();
let ContentsComponent = class ContentsComponent {
    constructor(router, contentService) {
        this.router = router;
        this.contentService = contentService;
        this.title = 'Contents';
    }
    ngOnInit() {
        let _self = this;
        this.contentService.init(function () {
            _self.getContents();
        });
    }
    getContents() {
        this.contentService.getContent().then(contents => this.contents = contents).catch(content_service_1.ContentService.handleError);
    }
    onSelect(content) {
        this.selectedContent = content;
    }
    gotoDetail() {
        this.router.navigate(['/detail', this.selectedContent.id]);
    }
    static handleError(error) {
        console.error('An error occurred', error);
        return Promise.reject(error.message || error);
    }
};
ContentsComponent = __decorate([
    core_1.Component({
        selector: 'contents',
        templateUrl: 'app/schedule/contents.component.html'
    }), 
    __metadata('design:paramtypes', [(typeof (_a = typeof router_1.Router !== 'undefined' && router_1.Router) === 'function' && _a) || Object, content_service_1.ContentService])
], ContentsComponent);
exports.ContentsComponent = ContentsComponent;
var _a;
//# sourceMappingURL=contents.component.js.map