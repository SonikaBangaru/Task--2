import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Stock {
    private String symbol;
    private double price;

    public Stock(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

class Market {
    private Map<String, Stock> stocks = new HashMap<>();

    public Market() {
        stocks.put("AAPL", new Stock("AAPL", 150.0));
        stocks.put("GOOGL", new Stock("GOOGL", 2800.0));
        stocks.put("AMZN", new Stock("AMZN", 3500.0));
    }

    public Stock getStock(String symbol) {
        return stocks.get(symbol);
    }

    public void updatePrice(String symbol, double newPrice) {
        Stock stock = stocks.get(symbol);
        if (stock != null) {
            stock.setPrice(newPrice);
        }
    }

    public void displayMarketData() {
        System.out.println("Market Data:");
        for (Stock stock : stocks.values()) {
            System.out.println(stock.getSymbol() + ": $" + stock.getPrice());
        }
    }
}

class Portfolio {
    private Map<String, Integer> holdings = new HashMap<>();
    private double cash;

    public Portfolio(double initialCash) {
        this.cash = initialCash;
    }

    public void buyStock(String symbol, int quantity, double price) {
        double cost = quantity * price;
        if (cash >= cost) {
            holdings.put(symbol, holdings.getOrDefault(symbol, 0) + quantity);
            cash -= cost;
            System.out.println("Bought " + quantity + " shares of " + symbol);
        } else {
            System.out.println("Not enough cash to buy " + quantity + " shares of " + symbol);
        }
    }

    public void sellStock(String symbol, int quantity, double price) {
        int currentHolding = holdings.getOrDefault(symbol, 0);
        if (currentHolding >= quantity) {
            holdings.put(symbol, currentHolding - quantity);
            cash += quantity * price;
            System.out.println("Sold " + quantity + " shares of " + symbol);
        } else {
            System.out.println("Not enough shares to sell " + quantity + " shares of " + symbol);
        }
    }

    public void displayPortfolio() {
        System.out.println("Portfolio:");
        for (Map.Entry<String, Integer> entry : holdings.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " shares");
        }
        System.out.println("Cash: $" + cash);
    }
}

public class StockTradingPlatform {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Market market = new Market();
        Portfolio portfolio = new Portfolio(10000.0);

        while (true) {
            System.out.println("1. Display Market Data");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. Display Portfolio");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    market.displayMarketData();
                    break;
                case 2:
                    System.out.print("Enter stock symbol to buy: ");
                    String buySymbol = scanner.next();
                    System.out.print("Enter quantity to buy: ");
                    int buyQuantity = scanner.nextInt();
                    Stock buyStock = market.getStock(buySymbol);
                    if (buyStock != null) {
                        portfolio.buyStock(buySymbol, buyQuantity, buyStock.getPrice());
                    } else {
                        System.out.println("Invalid stock symbol");
                    }
                    break;
                case 3:
                    System.out.print("Enter stock symbol to sell: ");
                    String sellSymbol = scanner.next();
                    System.out.print("Enter quantity to sell: ");
                    int sellQuantity = scanner.nextInt();
                    Stock sellStock = market.getStock(sellSymbol);
                    if (sellStock != null) {
                        portfolio.sellStock(sellSymbol, sellQuantity, sellStock.getPrice());
                    } else {
                        System.out.println("Invalid stock symbol");
                    }
                    break;
                case 4:
                    portfolio.displayPortfolio();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option");
            }
        }
    }
}
