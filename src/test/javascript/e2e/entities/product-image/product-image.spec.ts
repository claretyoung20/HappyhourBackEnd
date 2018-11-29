/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ProductImageComponentsPage, ProductImageDeleteDialog, ProductImageUpdatePage } from './product-image.page-object';

const expect = chai.expect;

describe('ProductImage e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let productImageUpdatePage: ProductImageUpdatePage;
    let productImageComponentsPage: ProductImageComponentsPage;
    let productImageDeleteDialog: ProductImageDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load ProductImages', async () => {
        await navBarPage.goToEntity('product-image');
        productImageComponentsPage = new ProductImageComponentsPage();
        expect(await productImageComponentsPage.getTitle()).to.eq('happybourBackEndApp.productImage.home.title');
    });

    it('should load create ProductImage page', async () => {
        await productImageComponentsPage.clickOnCreateButton();
        productImageUpdatePage = new ProductImageUpdatePage();
        expect(await productImageUpdatePage.getPageTitle()).to.eq('happybourBackEndApp.productImage.home.createOrEditLabel');
        await productImageUpdatePage.cancel();
    });

    it('should create and save ProductImages', async () => {
        const nbButtonsBeforeCreate = await productImageComponentsPage.countDeleteButtons();

        await productImageComponentsPage.clickOnCreateButton();
        await productImageUpdatePage.setImageUrlInput('imageUrl');
        expect(await productImageUpdatePage.getImageUrlInput()).to.eq('imageUrl');
        await productImageUpdatePage.productSelectLastOption();
        await productImageUpdatePage.save();
        expect(await productImageUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await productImageComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last ProductImage', async () => {
        const nbButtonsBeforeDelete = await productImageComponentsPage.countDeleteButtons();
        await productImageComponentsPage.clickOnLastDeleteButton();

        productImageDeleteDialog = new ProductImageDeleteDialog();
        expect(await productImageDeleteDialog.getDialogTitle()).to.eq('happybourBackEndApp.productImage.delete.question');
        await productImageDeleteDialog.clickOnConfirmButton();

        expect(await productImageComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
