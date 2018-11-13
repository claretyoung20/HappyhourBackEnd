export interface IOrderStatus {
    id?: number;
    name?: string;
}

export class OrderStatus implements IOrderStatus {
    constructor(public id?: number, public name?: string) {}
}
