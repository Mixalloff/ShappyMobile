package com.example.mikhail.stockstore.Classes;

/**
 * Created by mikhail on 19.12.15.
 */
public class APIConstants {

    public final static String USER_AUTH = "userauth";
    public final static String USER_REGISTER = "register";
    public final static String USER_ADD_STOCK = "useraddstock";
    public final static String USER_GET_FEED = "usergetfeed";
    public final static String GET_ALL_STOCKS = "getallstocks";
    public final static String GET_ALL_COMPANIES = "getallcompanies";
    public final static String GET_ALL_CATEGORIES = "getAllCategories";
    public final static String USER_GET_STOCKS_BY_COMPANY = "getStocksByCompany";
    public final static String USER_GET_STOCKS_BY_WORDPATH = "getStocksByWord";
    public final static String USER_GET_STOCKS_BY_FILTER = "getStocksByFilter";

    public final static String USER_AUTH_ROUTE = "user/authorize";
    public final static String USER_REGISTER_ROUTE = "user/register";
    public final static String GET_ALL_STOCKS_ROUTE = "user/stocks/all";
    public final static String USER_ADD_STOCK_ROUTE = "user/stocks/subscribe";
    public final static String USER_GET_FEED_ROUTE = "user/stocks/feed";
    public final static String GET_ALL_COMPANIES_ROUTE = "user/companies/all";
    public final static String GET_ALL_CATEGORIES_ROUTE = "";

    public final static String USER_GET_STOCKS_BY_COMPANY_ROUTE = "user/stocks/filter/company";
    public final static String USER_GET_STOCKS_BY_WORDPATH_ROUTE = "user/stocks/filter/search";
    public final static String USER_GET_STOCKS_BY_FILTER_ROUTE = "user/stocks/filter";
}