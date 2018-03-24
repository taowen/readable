// bad

class CarRental {
    protected float fuelConsumed;
    protected int days;
    protected Model model;
    protected float insuranceRate;
    protected boolean hasInsurance;
    protected boolean hasRefuelOnReturn;
    protected float refuelPrice;
    public CarRental(Model m, int rentalDays) {
        model = m;
        days = rentalDays;
        hasInsurance = false;
        hasRefuelOnReturn = false;
    }
    public float calcPrice() {
        float price = (model.price * days);
        if (hasInsurance)
            price += insuranceAmount();
        if (hasRefuelOnReturn)
            price += refuelPrice();
        return price;
    }
    public int getDaysRented() {
        return days;
    }
    public Model getModel() {
        return model;
    }
    public float getFuelConsumed() {
        return fuelConsumed;
    }
    public void setFuelConsumed(float amount) {
        fuelConsumed = amount;
    }
    private float insuranceAmount() {
        return insuranceRate * getDaysRented();
    }
    public void setInsurance(float rate) {
        insuranceRate = rate;
        hasInsurance = true;
    }
    private float refuelPrice() {
        return (getModel().fuelCapacity - getFuelConsumed()) * refuelPrice;
    }
    public void setRefuelOnReturn(float pricePerGallon) {
        refuelPrice = pricePerGallon;
        hasRefuelOnReturn = true;
    }
}
class Model {
    public float fuelCapacity;
    public float price;
    public String name;
    public Model(float fuelCapacity, float price, String name) {
        this.fuelCapacity = fuelCapacity;
        this.price = price;
        this.name = name;
    }
}

// good

interface Rental {
    public float calcPrice();
    public int getDaysRented();
    public float getFuelConsumed();
    public void setFuelConsumed(float amount);
    public Model getModel();
}
class CarRentalDecorator implements Rental {
    protected Rental rental;
    protected CarRentalDecorator(Rental r) {
        rental = r;
    }
    public float calcPrice() {
        return rental.calcPrice();
    }
    public int getDaysRented() {
        return rental.getDaysRented();
    }
    public float getFuelConsumed() {
        return rental.getFuelConsumed();
    }
    public void setFuelConsumed(float amount) {
        rental.setFuelConsumed(amount);
    }
    public Model getModel() {
        return rental.getModel();
    }
}
class Insurance extends CarRentalDecorator {
    protected float rate;
    public Insurance(Rental r, float rate) {
        super(r);
        this.rate = rate;
    }
    private float insuranceAmount() {
        return rate * rental.getDaysRented();
    }
    public float calcPrice() {
        return rental.calcPrice() + insuranceAmount();
    }
}
class RefuelOnReturn extends CarRentalDecorator {
    private float refuelPrice;
    public RefuelOnReturn(Rental r, float refuelPrice) {
        super(r);
        this.refuelPrice = refuelPrice;
    }
    private float refuelPrice() {
        return (rental.getModel().fuelCapacity - rental.getFuelConsumed()) * refuelPrice;
    }
    public float calcPrice() {
        return rental.calcPrice() + refuelPrice();
    }
}

Model m = new Model(10.0 f, 50.0 f, "Ford Taurus");
Rental insuredFord = new Insurance(new CarRental(m, 5), 12.5 f);
Rental refuelInsuredFord = new RefuelOnReturn(insuredFord, 3.75 f);
float price = refuelInsuredFord.calcPrice();
assert(price == 350.0 f);
Rental refuelFord = new RefuelOnReturn(new CarRental(m, 5), 3.75 f);
Rental insuredRefuelFord = new Insurance(refuelFord, 12.5 f);
float price = insuredRefuelFord.calcPrice();
assert(insuredFordPrice == 350.0 f);
