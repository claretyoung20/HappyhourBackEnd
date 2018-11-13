export interface IProductImage {
    id?: number;
    imageUrl?: string;
    productId?: number;
}

export class ProductImage implements IProductImage {
    constructor(public id?: number, public imageUrl?: string, public productId?: number) {}
}
