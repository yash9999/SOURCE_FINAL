package com.example.yogeshgarg.source.mvp.dashboard;

import android.app.Activity;
import android.database.Cursor;

import com.example.yogeshgarg.source.R;
import com.example.yogeshgarg.source.common.database.DatabaseHelper;
import com.example.yogeshgarg.source.common.helper.Progress;
import com.example.yogeshgarg.source.common.requestResponse.ApiAdapter;
import com.example.yogeshgarg.source.mvp.dashboard.model.DashboardExpiryProductModel;
import com.example.yogeshgarg.source.mvp.dashboard.model.DashboardInStoreModel;
import com.example.yogeshgarg.source.mvp.dashboard.model.DashboardPlanogramModel;
import com.example.yogeshgarg.source.mvp.dashboard.model.DashboardRecentUpdateModel;
import com.example.yogeshgarg.source.mvp.dashboard.model.NewProductModel;

import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by himanshu on 29/07/17.
 */

public class DashboardPresenterImpl implements DashboardPresenter {


    Activity activity;
    DashboardView dashboardView;

    JSONObject jsonObject;

    DatabaseHelper databaseHelper;

    public DashboardPresenterImpl(Activity activity, DashboardView dashboardView) {
        this.activity = activity;
        this.dashboardView = dashboardView;
        databaseHelper = new DatabaseHelper(activity);
    }


    //New product update
    @Override
    public void newProductApi(String timeOneMonthAgo) {
        try {
            ApiAdapter.getInstance(activity);
            callingNewProductApi(timeOneMonthAgo);
        } catch (ApiAdapter.NoInternetException ex) {
            dashboardView.onInternetErrorOfNewProductUpdate();
        }
    }

    private void callingNewProductApi(String timeOneMonthAgo) {
        Progress.start(activity);

        try {
            jsonObject = new JSONObject();
            //jsonObject.put(Const.KEY_DATE, timeOneMonthAgo);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (new JSONObject().toString()));

        Call<NewProductModel> getRegisterMessage = ApiAdapter.getApiService().gettingResultOfNewProductUpdate("application/json", "no-cache", body);

        getRegisterMessage.enqueue(new Callback<NewProductModel>() {
            @Override
            public void onResponse(Call<NewProductModel> call, Response<NewProductModel> response) {

                Progress.stop();
                try {
                    NewProductModel newProductModel = response.body();

                    if (newProductModel.getSuccessful()) {
                        ArrayList<NewProductModel.Result> arrayListResult = newProductModel.getResult();
                        dashboardView.onSuccessOfNewProductUpdate(arrayListResult);
                        insertNewProductData(arrayListResult);
                    } else {
                        dashboardView.onUnsccessOfNewProductUpdate(newProductModel.getMessage());
                    }
                } catch (NullPointerException exp) {
                    dashboardView.onUnsccessOfNewProductUpdate(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<NewProductModel> call, Throwable t) {
                Progress.stop();
                t.printStackTrace();
                dashboardView.onUnsccessOfNewProductUpdate(activity.getString(R.string.server_error));
            }
        });
    }

    //------------------Recent Price update
    @Override
    public void recentPriceUpdateApi(String timeOneMonthAgo) {
        try {
            ApiAdapter.getInstance(activity);
            callingRecentPriceUpdate(timeOneMonthAgo);
        } catch (ApiAdapter.NoInternetException ex) {
            dashboardView.onInternetErrorOfRecentPriceUpdate();
        }
    }

    private void callingRecentPriceUpdate(String timeOneMonthAgo) {
        Progress.start(activity);

        try {
            jsonObject = new JSONObject();
            //jsonObject.put(Const.KEY_DATE, timeOneMonthAgo);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (new JSONObject().toString()));

        Call<DashboardRecentUpdateModel> getRecentPriceUpdate = ApiAdapter.getApiService().gettingResultOfResentPriceUpdateProduct("application/json", "no-cache", body);

        getRecentPriceUpdate.enqueue(new Callback<DashboardRecentUpdateModel>() {
            @Override
            public void onResponse(Call<DashboardRecentUpdateModel> call, Response<DashboardRecentUpdateModel> response) {

                Progress.stop();
                try {
                    DashboardRecentUpdateModel dashboardRecentUpdateModel = response.body();

                    if (dashboardRecentUpdateModel.getSuccessful()) {
                        ArrayList<DashboardRecentUpdateModel.Result> arrayListRecentUpdate = dashboardRecentUpdateModel.getResult();
                        insertRecentPriceUpdated(arrayListRecentUpdate);
                        dashboardView.onSuccessOfRecentPriceUpdate(arrayListRecentUpdate);
                    } else {
                        dashboardView.onUnsccessOfRecentPriceUpdate(dashboardRecentUpdateModel.getMessage());
                    }
                } catch (NullPointerException exp) {
                    dashboardView.onUnsccessOfRecentPriceUpdate(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<DashboardRecentUpdateModel> call, Throwable t) {
                Progress.stop();
                t.printStackTrace();
                dashboardView.onUnsccessOfRecentPriceUpdate(activity.getString(R.string.server_error));
            }
        });

    }

    //expiry product
    @Override
    public void expiryProductApi(String timeOneMonthAgo) {
        try {
            ApiAdapter.getInstance(activity);
            callingExpiryProductApi(timeOneMonthAgo);
        } catch (ApiAdapter.NoInternetException ex) {
            dashboardView.onInternetErrorOfExpiryProduct();
        }
    }


    private void callingExpiryProductApi(String timeOneMonthAgo) {
        Progress.start(activity);

        try {
            jsonObject = new JSONObject();
            //jsonObject.put(Const.KEY_DATE, timeOneMonthAgo);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (new JSONObject().toString()));

        Call<DashboardExpiryProductModel> gotResultOfExpiryProduct = ApiAdapter.getApiService().gettingResultOfExpiryProduct("application/json", "no-cache", body);

        gotResultOfExpiryProduct.enqueue(new Callback<DashboardExpiryProductModel>() {
            @Override
            public void onResponse(Call<DashboardExpiryProductModel> call, Response<DashboardExpiryProductModel> response) {

                Progress.stop();
                try {
                    DashboardExpiryProductModel dashboardExpiryProductModel = response.body();

                    if (dashboardExpiryProductModel.getSuccessful()) {
                        ArrayList<DashboardExpiryProductModel.Result> arrayListResult = dashboardExpiryProductModel.getResult();
                        insertExpiryProduct(dashboardExpiryProductModel.getResult());
                        dashboardView.onSuccessOfExpiryProduct(dashboardExpiryProductModel.getResult());
                    } else {
                        dashboardView.onUnsccessOfExpiryProduct(dashboardExpiryProductModel.getMessage());
                    }
                } catch (NullPointerException exp) {
                    dashboardView.onUnsccessOfExpiryProduct(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<DashboardExpiryProductModel> call, Throwable t) {
                Progress.stop();
                t.printStackTrace();
                dashboardView.onUnsccessOfExpiryProduct(activity.getString(R.string.server_error));
            }
        });

    }


    //Store sampling

    @Override
    public void storeSamplingProductApi(String timeOneMonthAgo) {
        try {
            ApiAdapter.getInstance(activity);
            callingStoreSamplingApi(timeOneMonthAgo);
        } catch (ApiAdapter.NoInternetException ex) {
            dashboardView.onInternetErrorOfInstoreSampling();
        }
    }

    private void callingStoreSamplingApi(String timeOneMonthAgo) {
        Progress.start(activity);

        try {
            jsonObject = new JSONObject();
            // jsonObject.put(Const.KEY_DATE, timeOneMonthAgo);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (new JSONObject().toString()));

        Call<DashboardInStoreModel> gotResultOfSamplingProduct = ApiAdapter.getApiService().gettingResultOfSamplingProduct("application/json", "no-cache", body);

        gotResultOfSamplingProduct.enqueue(new Callback<DashboardInStoreModel>() {
            @Override
            public void onResponse(Call<DashboardInStoreModel> call, Response<DashboardInStoreModel> response) {

                Progress.stop();
                try {
                    DashboardInStoreModel dashboardInStoreModel = response.body();

                    if (dashboardInStoreModel.getSuccessful()) {
                        ArrayList<DashboardInStoreModel.Result> arrayListResult = dashboardInStoreModel.getResult();
                        insertSamplingProduct(arrayListResult);
                        dashboardView.onSuccessOfInstoreSampling(arrayListResult);
                    } else {
                        dashboardView.onUnsccessOfInstoreSampling(dashboardInStoreModel.getMessage());
                    }
                } catch (NullPointerException exp) {
                    dashboardView.onUnsccessOfInstoreSampling(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<DashboardInStoreModel> call, Throwable t) {
                Progress.stop();
                t.printStackTrace();
                dashboardView.onUnsccessOfInstoreSampling(activity.getString(R.string.server_error));
            }
        });

    }

    @Override
    public void planogramApi() {
        try {
            ApiAdapter.getInstance(activity);
            callingPlanogramApi();
        } catch (ApiAdapter.NoInternetException ex) {
            dashboardView.onInternetErrorPlanogram();
        }
    }

    @Override
    public void getNewProductTable() {
        getNewProductDb();
    }

    @Override
    public void getExpiryTable() {
        getExpiryProductDb();
    }

    @Override
    public void getInStoreTable() {
        getInStoreProductDb();
    }

    @Override
    public void getRecentUpdateTable() {
        getRecentPriceDb();
    }

    @Override
    public void getPlanogramTable() {
        getPlanogramDataDb();
    }

    private void callingPlanogramApi() {
        Progress.start(activity);

        try {
            jsonObject = new JSONObject();
            //jsonObject.put(Const.KEY_DATE, timeOneMonthAgo);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (new JSONObject().toString()));

        Call<DashboardPlanogramModel> getPlanogramResult = ApiAdapter.getApiService().gettingResultOfPlanogram("application/json", "no-cache", body);

        getPlanogramResult.enqueue(new Callback<DashboardPlanogramModel>() {
            @Override
            public void onResponse(Call<DashboardPlanogramModel> call, Response<DashboardPlanogramModel> response) {

                Progress.stop();
                try {
                    DashboardPlanogramModel dashboardPlanogramModel = response.body();

                    if (dashboardPlanogramModel.getSuccessful()) {
                        ArrayList<DashboardPlanogramModel.Result> arrayListResult = dashboardPlanogramModel.getResult();
                        insertPlanogram(arrayListResult);
                        dashboardView.onSuccessPlanogram(arrayListResult);
                    } else {
                        dashboardView.onUnsuccessPlanogram(dashboardPlanogramModel.getMessage());
                    }
                } catch (NullPointerException exp) {
                    dashboardView.onUnsuccessPlanogram(activity.getString(R.string.server_error));
                }
            }

            @Override
            public void onFailure(Call<DashboardPlanogramModel> call, Throwable t) {
                Progress.stop();
                t.printStackTrace();
                dashboardView.onUnsuccessPlanogram(activity.getString(R.string.server_error));
            }
        });
    }

    //Insert new Product
    private void insertNewProductData(ArrayList<NewProductModel.Result> arrayListResult) {

        databaseHelper.deleteTable(DatabaseHelper.TABLE_NEW_PRODUCT);

        for (int i = 0; i < arrayListResult.size(); i++) {
            NewProductModel.Result result = arrayListResult.get(i);
            String storeId = result.getStoreId();
            String storeName = result.getStoreName();
            String address = result.getAddress();
            String location_id = result.getLocationId();
            String country = result.getCountry();
            String city = result.getCity();
            String latitude = result.getLatitude();
            String longitude = result.getLongitude();
            String brand_name = result.getBrandName();
            String product_name = result.getProductName();
            String upc = result.getUpc();
            String weight = result.getWeight();
            String tax = result.getTax();
            String cost = result.getCost();
            String discount = result.getDiscount();

            String link = result.getLink();
            byte[] imageByte=null;


            String comment = result.getComment();
            String scan_id = result.getScanId();
            String date_added = result.getDateadded();
            String type = result.getType();

            databaseHelper.insertNewProduct(storeId, storeName, address, location_id, country, city, latitude, longitude, brand_name, product_name, upc, weight, tax, cost, discount,imageByte, comment, scan_id, date_added, type);
        }
    }

    private void insertRecentPriceUpdated(ArrayList<DashboardRecentUpdateModel.Result> arrayListResult) {

        databaseHelper.deleteTable(DatabaseHelper.TABLE_RECENT_UPDATE);

        for (int i = 0; i < arrayListResult.size(); i++) {
            DashboardRecentUpdateModel.Result result = arrayListResult.get(i);
            String storeId = result.getStoreId();
            String storeName = result.getStoreName();
            String address = result.getAddress();
            String location_id = result.getLocationId();
            String country = result.getCountry();
            String city = result.getCity();
            String latitude = result.getLatitude();
            String longitude = result.getLongitude();
            String category_name = result.getCategoryName();
            String category_id = result.getCategoryName();
            String brand_name = result.getBrandName();
            String brand_id = result.getBrandId();
            String product_name = result.getProductName();
            String product_id = result.getProductId();
            String range_start = result.getRangestart();
            String range_end = result.getRangeend();
            String range_type = result.getRangetype();

            String image = result.getImage();
            byte[] imageByte=null;

            String upc = result.getUpc();
            String weight = result.getWeight();
            String tax = result.getTax();
            String cost = result.getCost();
            String discount = result.getDiscount();
            String currency_id = result.getCurrencyId();
            String currency_name = result.getCurrencyName();
            String tax_value = result.getTaxValue();
            String date_added = result.getDateadded();
            String userId = result.getUserId();
            String first_name = result.getFirstName();
            String last_name = result.getLastName();
            String profile_pic = result.getProfilePic();
            String last_updated = result.getLastupdated();
            String stock = result.getStock();
            String stock_last_updated = result.getStocklastupdated();
            String stock_in_measure = result.getStockUnitMeasure();
            String item_in_measure = result.getItemUnitMeasure();
            String type = result.getType();

            databaseHelper.insertRecentUpdateData(storeId, storeName, address, location_id, country, city, latitude, longitude, category_name, category_id, brand_name, brand_id, product_name, product_id, range_start, range_end, range_type, imageByte, upc, weight, tax, cost, discount, currency_id, currency_name, tax_value, date_added, userId, first_name, last_name, profile_pic, last_updated, stock, stock_last_updated, stock_in_measure, item_in_measure, type);

        }
    }

    private void insertExpiryProduct(ArrayList<DashboardExpiryProductModel.Result> arrayListResult) {

        databaseHelper.deleteTable(DatabaseHelper.TABLE_EXPIRY_PRODUCT);

        for (int i = 0; i < arrayListResult.size(); i++) {
            DashboardExpiryProductModel.Result result = arrayListResult.get(i);

            String storeId = result.getStoreId();
            String storeName = result.getStoreName();
            String address = result.getAddress();
            String location_id = result.getLocationId();
            String country = result.getCountry();
            String city = result.getCity();
            String latitude = result.getLatitude();
            String longitude = result.getLongitude();
            String category_name = result.getCategoryName();
            String category_id = result.getCategoryName();
            String brand_name = result.getBrandName();
            String brand_id = result.getBrandId();
            String product_name = result.getProductName();
            String product_id = result.getProductId();
            String range_start = result.getRangestart();
            String range_end = result.getRangeend();
            String range_type = result.getRangetype();

            String image = result.getImage();
            byte[] imageByte=null;

            String upc = result.getUpc();
            String weight = result.getWeight();
            String sampling_date = result.getSamplingDate();
            String currency_id = result.getCurrencyId();
            String currency_name = result.getCurrencyName();
            String tax_value = result.getTaxValue();
            String date_added = result.getDateadded();
            String userId = result.getUserId();
            String first_name = result.getFirstName();
            String last_name = result.getLastName();
            String profile_pic = result.getProfilePic();
            String comment = result.getComment();
            String stock = result.getStock();
            String stock_in_measure = result.getStockUnitMeasure();
            String item_in_measure = result.getItemUnitMeasure();
            String start = result.getStart();
            String end = result.getEnd();


            databaseHelper.insertExpiredProduct(storeId, storeName, address, location_id, country, city, latitude, longitude, category_name, category_id, brand_name, brand_id, product_name, product_id, range_start, range_end, range_type, imageByte, upc, weight, sampling_date, currency_id, currency_name, tax_value, date_added, userId, first_name, last_name, profile_pic, comment, stock, stock_in_measure, item_in_measure, start, end);

        }

    }

    private void insertSamplingProduct(ArrayList<DashboardInStoreModel.Result> arrayListResult) {

        databaseHelper.deleteTable(DatabaseHelper.TABLE_IN_STORE);

        for (int i = 0; i < arrayListResult.size(); i++) {
            DashboardInStoreModel.Result result = arrayListResult.get(i);

            String storeId = result.getStoreId();
            String storeName = result.getStoreName();
            String address = result.getAddress();
            String location_id = result.getLocationId();
            String country = result.getCountry();
            String city = result.getCity();
            String latitude = result.getLatitude();
            String longitude = result.getLongitude();
            String category_name = result.getCategoryName();
            String category_id = result.getCategoryName();
            String brand_name = result.getBrandName();
            String brand_id = result.getBrandId();
            String product_name = result.getProductName();
            String product_id = result.getProductId();
            String range_start = result.getRangestart();
            String range_end = result.getRangeend();
            String range_type = result.getRangetype();

            String image = result.getImage();
            byte[] imageByte=null;

            String upc = result.getUpc();
            String weight = result.getWeight();
            String sampling_date = result.getSamplingDate();
            String currency_id = result.getCurrencyId();
            String currency_name = result.getCurrencyName();
            String tax_value = result.getTaxValue();
            String date_added = result.getDateadded();
            String userId = result.getUserId();
            String first_name = result.getFirstName();
            String last_name = result.getLastName();
            String profile_pic = result.getProfilePic();
            String comment = result.getComment();
            String stock_in_measure = result.getStockUnitMeasure();
            String item_in_measure = result.getItemUnitMeasure();


            databaseHelper.insertInStore(storeId, storeName, address, location_id, country, city, latitude, longitude, category_name, category_id, brand_name, brand_id, product_name, product_id, range_start, range_end, range_type, imageByte, upc, weight, sampling_date, currency_id, currency_name, tax_value, date_added, userId, first_name, last_name, profile_pic, comment, stock_in_measure, item_in_measure);

        }

    }

    private void insertPlanogram(ArrayList<DashboardPlanogramModel.Result> arrayListResult) {

        databaseHelper.deleteTable(DatabaseHelper.TABLE_PLANOGRAM);


        for (int i = 0; i < arrayListResult.size(); i++) {
            DashboardPlanogramModel.Result result = arrayListResult.get(i);
            String title = result.getTitle();
            String planogramId = result.getPlanogramId();
            String message = result.getMessage();

            String link = result.getLink();
            byte[] image=null;

            String date_added = result.getDateadded();

            databaseHelper.insertPlanogram(title, planogramId, message, image, date_added);
        }

    }

    private void getNewProductDb() {

        //get new product data
        ArrayList<NewProductModel.Result> arrayListNewProduct = new ArrayList<>();
        NewProductModel.Result result = new NewProductModel().new Result();

        Cursor cursorNewProduct = databaseHelper.getDataFromTable(DatabaseHelper.TABLE_NEW_PRODUCT);
        while (cursorNewProduct.moveToNext()) {
            int storeIdIndex = cursorNewProduct.getColumnIndex(DatabaseHelper.STORE_ID);
            String storeId = cursorNewProduct.getString(storeIdIndex);
            result.setStoreId(storeId);

            int storeNameIndex = cursorNewProduct.getColumnIndex(DatabaseHelper.STORE_NAME);
            String storeName = cursorNewProduct.getString(storeNameIndex);
            result.setStoreName(storeName);

            int addressIndex = cursorNewProduct.getColumnIndex(DatabaseHelper.ADDRESS);
            String address = cursorNewProduct.getString(addressIndex);
            result.setAddress(address);

            int locationIndex = cursorNewProduct.getColumnIndex(DatabaseHelper.LOCATION_ID);
            String location_id = cursorNewProduct.getString(locationIndex);
            result.setLocationId(location_id);

            int countryIndex = cursorNewProduct.getColumnIndex(DatabaseHelper.COUNTRY);
            String country = cursorNewProduct.getString(countryIndex);
            result.setCountry(country);

            int cityIndex = cursorNewProduct.getColumnIndex(DatabaseHelper.CITY);
            String city = cursorNewProduct.getString(cityIndex);
            result.setCity(city);

            int latitudeIndex = cursorNewProduct.getColumnIndex(DatabaseHelper.LATITUDE);
            String latitude = cursorNewProduct.getString(latitudeIndex);
            result.setLatitude(latitude);

            int longitudeIndex = cursorNewProduct.getColumnIndex(DatabaseHelper.LONGITUDE);
            String longitude = cursorNewProduct.getString(longitudeIndex);
            result.setLongitude(longitude);

            int brandNameIndex = cursorNewProduct.getColumnIndex(DatabaseHelper.BRAND_NAME);
            String brand_name = cursorNewProduct.getString(brandNameIndex);
            result.setBrandName(brand_name);

            int productNameIndex = cursorNewProduct.getColumnIndex(DatabaseHelper.PRODUCT_NAME);
            String product_name = cursorNewProduct.getString(productNameIndex);
            result.setProductName(product_name);

            int upcIndex = cursorNewProduct.getColumnIndex(DatabaseHelper.UPC);
            String upc = cursorNewProduct.getString(upcIndex);
            result.setUpc(upc);

            int weightIndex = cursorNewProduct.getColumnIndex(DatabaseHelper.WEIGHT);
            String weight = cursorNewProduct.getString(weightIndex);
            result.setWeight(weight);

            int taxIndex = cursorNewProduct.getColumnIndex(DatabaseHelper.TAX);
            String tax = cursorNewProduct.getString(taxIndex);
            result.setTax(tax);

            int costIndex = cursorNewProduct.getColumnIndex(DatabaseHelper.COST);
            String cost = cursorNewProduct.getString(costIndex);
            result.setCost(cost);

            int discountIndex = cursorNewProduct.getColumnIndex(DatabaseHelper.DISCOUNT);
            String discount = cursorNewProduct.getString(discountIndex);
            result.setDiscount(discount);

            int linkIndex = cursorNewProduct.getColumnIndex(DatabaseHelper.LINK);
            byte[] link = cursorNewProduct.getBlob(linkIndex);
            result.setLinkByte(link);

            int commentIndex = cursorNewProduct.getColumnIndex(DatabaseHelper.COMMENT);
            String comment = cursorNewProduct.getString(commentIndex);
            result.setComment(comment);

            int scanIdIndex = cursorNewProduct.getColumnIndex(DatabaseHelper.SCAN_ID);
            String scan_id = cursorNewProduct.getString(scanIdIndex);
            result.setScanId(scan_id);

            int dateAddedIndex = cursorNewProduct.getColumnIndex(DatabaseHelper.DATE_ADDED);
            String date_added = cursorNewProduct.getString(dateAddedIndex);
            result.setDateadded(date_added);

            int typeIndex = cursorNewProduct.getColumnIndex(DatabaseHelper.TYPE);
            String type = cursorNewProduct.getString(typeIndex);
            result.setType(type);

            arrayListNewProduct.add(result);
        }

        dashboardView.onSuccessOfNewProductUpdate(arrayListNewProduct);
    }

    private void getRecentPriceDb() {

        ArrayList<DashboardRecentUpdateModel.Result> arrayListRecentPrice = new ArrayList<>();
        DashboardRecentUpdateModel.Result resultRecent = new DashboardRecentUpdateModel().new Result();

        Cursor cursorRecent = databaseHelper.getDataFromTable(DatabaseHelper.TABLE_RECENT_UPDATE);

        if(cursorRecent!=null) {
            while (cursorRecent.moveToNext()) {
                int storeIdIndex = cursorRecent.getColumnIndex(DatabaseHelper.STORE_ID);
                String storeId = cursorRecent.getString(storeIdIndex);
                resultRecent.setStoreId(storeId);

                int storeNameIndex = cursorRecent.getColumnIndex(DatabaseHelper.STORE_NAME);
                String storeName = cursorRecent.getString(storeNameIndex);
                resultRecent.setStoreName(storeName);

                int addressIndex = cursorRecent.getColumnIndex(DatabaseHelper.ADDRESS);
                String address = cursorRecent.getString(addressIndex);
                resultRecent.setAddress(address);

                int locationIndex = cursorRecent.getColumnIndex(DatabaseHelper.LOCATION_ID);
                String location_id = cursorRecent.getString(locationIndex);
                resultRecent.setLocationId(location_id);

                int countryIndex = cursorRecent.getColumnIndex(DatabaseHelper.COUNTRY);
                String country = cursorRecent.getString(countryIndex);
                resultRecent.setCountry(country);

                int cityIndex = cursorRecent.getColumnIndex(DatabaseHelper.CITY);
                String city = cursorRecent.getString(cityIndex);
                resultRecent.setCity(city);

                int latitudeIndex = cursorRecent.getColumnIndex(DatabaseHelper.LATITUDE);
                String latitude = cursorRecent.getString(latitudeIndex);
                resultRecent.setLatitude(latitude);

                int longitudeIndex = cursorRecent.getColumnIndex(DatabaseHelper.LONGITUDE);
                String longitude = cursorRecent.getString(longitudeIndex);
                resultRecent.setLongitude(longitude);


                int brandIdIndex = cursorRecent.getColumnIndex(DatabaseHelper.BRAND_ID);
                String brand_id = cursorRecent.getString(brandIdIndex);
                resultRecent.setBrandId(brand_id);

                int brandNameIndex = cursorRecent.getColumnIndex(DatabaseHelper.BRAND_NAME);
                String brand_name = cursorRecent.getString(brandNameIndex);
                resultRecent.setBrandName(brand_name);

                int productIdIndex = cursorRecent.getColumnIndex(DatabaseHelper.PRODUCT_ID);
                String product_id = cursorRecent.getString(productIdIndex);
                resultRecent.setProductId(product_id);

                int productNameIndex = cursorRecent.getColumnIndex(DatabaseHelper.PRODUCT_NAME);
                String product_name = cursorRecent.getString(productNameIndex);
                resultRecent.setProductName(product_name);

                int upcIndex = cursorRecent.getColumnIndex(DatabaseHelper.UPC);
                String upc = cursorRecent.getString(upcIndex);
                resultRecent.setUpc(upc);

                int weightIndex = cursorRecent.getColumnIndex(DatabaseHelper.WEIGHT);
                String weight = cursorRecent.getString(weightIndex);
                resultRecent.setWeight(weight);


                int taxIndex = cursorRecent.getColumnIndex(DatabaseHelper.TAX);
                String tax = cursorRecent.getString(taxIndex);
                resultRecent.setTax(tax);

                int taxValueIndex = cursorRecent.getColumnIndex(DatabaseHelper.TAX_VALUE);
                String taxValue = cursorRecent.getString(taxValueIndex);
                resultRecent.setTaxValue(taxValue);

                int costIndex = cursorRecent.getColumnIndex(DatabaseHelper.COST);
                String cost = cursorRecent.getString(costIndex);
                resultRecent.setCost(cost);

                int discountIndex = cursorRecent.getColumnIndex(DatabaseHelper.DISCOUNT);
                String discount = cursorRecent.getString(discountIndex);
                resultRecent.setDiscount(discount);

                int categoryNameIndex = cursorRecent.getColumnIndex(DatabaseHelper.CATEGORY_NAME);
                String category_name = cursorRecent.getString(categoryNameIndex);
                resultRecent.setCategoryName(category_name);

                int categoryIdIndex = cursorRecent.getColumnIndex(DatabaseHelper.CATEGORY_ID);
                String category_id = cursorRecent.getString(categoryIdIndex);
                resultRecent.setCategoryId(category_id);

                int rangeStartIndex = cursorRecent.getColumnIndex(DatabaseHelper.RANGE_START);
                String rangeStart = cursorRecent.getString(rangeStartIndex);
                resultRecent.setRangestart(rangeStart);

                int rangeEndIndex = cursorRecent.getColumnIndex(DatabaseHelper.RANGE_END);
                String rangeEnd = cursorRecent.getString(rangeEndIndex);
                resultRecent.setRangeend(rangeEnd);

                int rangeTypeIndex = cursorRecent.getColumnIndex(DatabaseHelper.RANGE_TYPE);
                String rangeType = cursorRecent.getString(rangeTypeIndex);
                resultRecent.setRangetype(rangeType);

                int imageIndex = cursorRecent.getColumnIndex(DatabaseHelper.IMAGE);
                byte[] image = cursorRecent.getBlob(imageIndex);
                resultRecent.setImageByte(image);

                int currencyIdIndex = cursorRecent.getColumnIndex(DatabaseHelper.CURRENCY_ID);
                String currencyId = cursorRecent.getString(currencyIdIndex);
                resultRecent.setCurrencyId(currencyId);

                int currencyNameIndex = cursorRecent.getColumnIndex(DatabaseHelper.CURRENCY_NAME);
                String currencyName = cursorRecent.getString(currencyNameIndex);
                resultRecent.setCurrencyName(currencyName);

                int dateAddedIndex = cursorRecent.getColumnIndex(DatabaseHelper.DATE_ADDED);
                String date_added = cursorRecent.getString(dateAddedIndex);
                resultRecent.setDateadded(date_added);

                int typeIndex = cursorRecent.getColumnIndex(DatabaseHelper.TYPE);
                String type = cursorRecent.getString(typeIndex);
                resultRecent.setType(type);

                int userIdIndex = cursorRecent.getColumnIndex(DatabaseHelper.TYPE);
                String userId = cursorRecent.getString(userIdIndex);
                resultRecent.setUserId(userId);

                int firstNameIndex = cursorRecent.getColumnIndex(DatabaseHelper.FIRST_NAME);
                String firstName = cursorRecent.getString(firstNameIndex);
                resultRecent.setFirstName(firstName);

                int lastNameIndex = cursorRecent.getColumnIndex(DatabaseHelper.LAST_NAME);
                String lastName = cursorRecent.getString(lastNameIndex);
                resultRecent.setLastName(lastName);

                int profilePicIndex = cursorRecent.getColumnIndex(DatabaseHelper.PROFILE_PIC);
                String profilePic = cursorRecent.getString(profilePicIndex);
                resultRecent.setProfilePic(profilePic);

                int lastUpdatedIndex = cursorRecent.getColumnIndex(DatabaseHelper.LAST_UPDATED);
                String lastUpdated = cursorRecent.getString(lastUpdatedIndex);
                resultRecent.setLastupdated(lastUpdated);

                int stockLastUpdatedIndex = cursorRecent.getColumnIndex(DatabaseHelper.STOCK_LAST_UPDATED);
                String stockLastUpdated = cursorRecent.getString(stockLastUpdatedIndex);
                resultRecent.setLastupdated(stockLastUpdated);

                int stockIndex = cursorRecent.getColumnIndex(DatabaseHelper.STOCK);
                String stock = cursorRecent.getString(stockIndex);
                resultRecent.setStock(stock);


                int stockInMeasureIndex = cursorRecent.getColumnIndex(DatabaseHelper.STOCK_IN_MEASURE);
                String stockInMeasure = cursorRecent.getString(stockInMeasureIndex);
                resultRecent.setStockUnitMeasure(stockInMeasure);

                int itemInMeasureIndex = cursorRecent.getColumnIndex(DatabaseHelper.ITEM_IN_MEASURE);
                String itemInMeasure = cursorRecent.getString(itemInMeasureIndex);
                resultRecent.setItemUnitMeasure(itemInMeasure);

                arrayListRecentPrice.add(resultRecent);

            }
        }

        dashboardView.onSuccessOfRecentPriceUpdate(arrayListRecentPrice);

    }

    //<!------------------------------------Expiry Data----------------------------------------->

    private void getExpiryProductDb() {

        ArrayList<DashboardExpiryProductModel.Result> arrayListExpiry = new ArrayList<>();
        DashboardExpiryProductModel.Result resultExpiry = new DashboardExpiryProductModel().new Result();

            Cursor cursorExpiry = databaseHelper.getDataFromTable(DatabaseHelper.TABLE_EXPIRY_PRODUCT);

        if(cursorExpiry!=null)
        {
            while (cursorExpiry.moveToNext()) {
                int storeIdIndex = cursorExpiry.getColumnIndex(DatabaseHelper.STORE_ID);
                String storeId = cursorExpiry.getString(storeIdIndex);
                resultExpiry.setStoreId(storeId);

                int storeNameIndex = cursorExpiry.getColumnIndex(DatabaseHelper.STORE_NAME);
                String storeName = cursorExpiry.getString(storeNameIndex);
                resultExpiry.setStoreName(storeName);

                int addressIndex = cursorExpiry.getColumnIndex(DatabaseHelper.ADDRESS);
                String address = cursorExpiry.getString(addressIndex);
                resultExpiry.setAddress(address);

                int locationIndex = cursorExpiry.getColumnIndex(DatabaseHelper.LOCATION_ID);
                String location_id = cursorExpiry.getString(locationIndex);
                resultExpiry.setLocationId(location_id);

                int countryIndex = cursorExpiry.getColumnIndex(DatabaseHelper.COUNTRY);
                String country = cursorExpiry.getString(countryIndex);
                resultExpiry.setCountry(country);

                int cityIndex = cursorExpiry.getColumnIndex(DatabaseHelper.CITY);
                String city = cursorExpiry.getString(cityIndex);
                resultExpiry.setCity(city);

                int latitudeIndex = cursorExpiry.getColumnIndex(DatabaseHelper.LATITUDE);
                String latitude = cursorExpiry.getString(latitudeIndex);
                resultExpiry.setLatitude(latitude);

                int longitudeIndex = cursorExpiry.getColumnIndex(DatabaseHelper.LONGITUDE);
                String longitude = cursorExpiry.getString(longitudeIndex);
                resultExpiry.setLongitude(longitude);


                int brandIdIndex = cursorExpiry.getColumnIndex(DatabaseHelper.BRAND_ID);
                String brand_id = cursorExpiry.getString(brandIdIndex);
                resultExpiry.setBrandId(brand_id);

                int brandNameIndex = cursorExpiry.getColumnIndex(DatabaseHelper.BRAND_NAME);
                String brand_name = cursorExpiry.getString(brandNameIndex);
                resultExpiry.setBrandName(brand_name);

                int productIdIndex = cursorExpiry.getColumnIndex(DatabaseHelper.PRODUCT_ID);
                String product_id = cursorExpiry.getString(productIdIndex);
                resultExpiry.setProductId(product_id);

                int productNameIndex = cursorExpiry.getColumnIndex(DatabaseHelper.PRODUCT_NAME);
                String product_name = cursorExpiry.getString(productNameIndex);
                resultExpiry.setProductName(product_name);

                int upcIndex = cursorExpiry.getColumnIndex(DatabaseHelper.UPC);
                String upc = cursorExpiry.getString(upcIndex);
                resultExpiry.setUpc(upc);

                int weightIndex = cursorExpiry.getColumnIndex(DatabaseHelper.WEIGHT);
                String weight = cursorExpiry.getString(weightIndex);
                resultExpiry.setWeight(weight);

                int categoryNameIndex = cursorExpiry.getColumnIndex(DatabaseHelper.CATEGORY_NAME);
                String category_name = cursorExpiry.getString(categoryNameIndex);
                resultExpiry.setCategoryName(category_name);

                int categoryIdIndex = cursorExpiry.getColumnIndex(DatabaseHelper.CATEGORY_ID);
                String category_id = cursorExpiry.getString(categoryIdIndex);
                resultExpiry.setCategoryId(category_id);

                int rangeStartIndex = cursorExpiry.getColumnIndex(DatabaseHelper.RANGE_START);
                String rangeStart = cursorExpiry.getString(rangeStartIndex);
                resultExpiry.setRangestart(rangeStart);

                int rangeEndIndex = cursorExpiry.getColumnIndex(DatabaseHelper.RANGE_END);
                String rangeEnd = cursorExpiry.getString(rangeEndIndex);
                resultExpiry.setRangeend(rangeEnd);

                int rangeTypeIndex = cursorExpiry.getColumnIndex(DatabaseHelper.RANGE_TYPE);
                String rangeType = cursorExpiry.getString(rangeTypeIndex);
                resultExpiry.setRangetype(rangeType);

                int imageIndex = cursorExpiry.getColumnIndex(DatabaseHelper.IMAGE);
                byte[] image = cursorExpiry.getBlob(imageIndex);
                resultExpiry.setImageByte(image);

                int currencyIdIndex = cursorExpiry.getColumnIndex(DatabaseHelper.CURRENCY_ID);
                String currencyId = cursorExpiry.getString(currencyIdIndex);
                resultExpiry.setCurrencyId(currencyId);

                int currencyNameIndex = cursorExpiry.getColumnIndex(DatabaseHelper.CURRENCY_NAME);
                String currencyName = cursorExpiry.getString(currencyNameIndex);
                resultExpiry.setCurrencyName(currencyName);

                int dateAddedIndex = cursorExpiry.getColumnIndex(DatabaseHelper.DATE_ADDED);
                String date_added = cursorExpiry.getString(dateAddedIndex);
                resultExpiry.setDateadded(date_added);

                int userIdIndex = cursorExpiry.getColumnIndex(DatabaseHelper.USER_ID);
                String userId = cursorExpiry.getString(userIdIndex);
                resultExpiry.setUserId(userId);

                int firstNameIndex = cursorExpiry.getColumnIndex(DatabaseHelper.FIRST_NAME);
                String firstName = cursorExpiry.getString(firstNameIndex);
                resultExpiry.setFirstName(firstName);

                int lastNameIndex = cursorExpiry.getColumnIndex(DatabaseHelper.LAST_NAME);
                String lastName = cursorExpiry.getString(lastNameIndex);
                resultExpiry.setLastName(lastName);

                int profilePicIndex = cursorExpiry.getColumnIndex(DatabaseHelper.PROFILE_PIC);
                String profilePic = cursorExpiry.getString(profilePicIndex);
                resultExpiry.setProfilePic(profilePic);

                int taxValueIndex = cursorExpiry.getColumnIndex(DatabaseHelper.TAX_VALUE);
                String taxValue = cursorExpiry.getString(taxValueIndex);
                resultExpiry.setTaxValue(taxValue);

                int stockIndex = cursorExpiry.getColumnIndex(DatabaseHelper.STOCK);
                String stock = cursorExpiry.getString(stockIndex);
                resultExpiry.setStock(stock);

                int stockInMeasureIndex = cursorExpiry.getColumnIndex(DatabaseHelper.STOCK_IN_MEASURE);
                String stockInMeasure = cursorExpiry.getString(stockInMeasureIndex);
                resultExpiry.setStockUnitMeasure(stockInMeasure);

                int itemInMeasureIndex = cursorExpiry.getColumnIndex(DatabaseHelper.ITEM_IN_MEASURE);
                String itemInMeasure = cursorExpiry.getString(itemInMeasureIndex);
                resultExpiry.setItemUnitMeasure(itemInMeasure);

                int samplingDateIndex = cursorExpiry.getColumnIndex(DatabaseHelper.SAMPLING_DATE);
                String samplingDate = cursorExpiry.getString(samplingDateIndex);
                resultExpiry.setSamplingDate(samplingDate);

                int commentIndex = cursorExpiry.getColumnIndex(DatabaseHelper.COMMENT);
                String comment = cursorExpiry.getString(commentIndex);
                resultExpiry.setComment(comment);

                int startIndex = cursorExpiry.getColumnIndex(DatabaseHelper.START);
                String start = cursorExpiry.getString(startIndex);
                resultExpiry.setStart(start);

                int endIndex = cursorExpiry.getColumnIndex(DatabaseHelper.END);
                String end = cursorExpiry.getString(endIndex);
                resultExpiry.setEnd(end);


                arrayListExpiry.add(resultExpiry);
            }
        }

        dashboardView.onSuccessOfExpiryProduct(arrayListExpiry);

    }

    //<!------------------------------------In Store Data----------------------------------------->

    public void getInStoreProductDb() {

        ArrayList<DashboardInStoreModel.Result> arrayListInStore = new ArrayList<>();
        DashboardInStoreModel.Result resultInStore = new DashboardInStoreModel().new Result();

        Cursor cursorInStore = databaseHelper.getDataFromTable(DatabaseHelper.TABLE_IN_STORE);

        if(cursorInStore!=null) {
            while (cursorInStore.moveToNext()) {
                int storeIdIndex = cursorInStore.getColumnIndex(DatabaseHelper.STORE_ID);
                String storeId = cursorInStore.getString(storeIdIndex);
                resultInStore.setStoreId(storeId);

                int storeNameIndex = cursorInStore.getColumnIndex(DatabaseHelper.STORE_NAME);
                String storeName = cursorInStore.getString(storeNameIndex);
                resultInStore.setStoreName(storeName);

                int addressIndex = cursorInStore.getColumnIndex(DatabaseHelper.ADDRESS);
                String address = cursorInStore.getString(addressIndex);
                resultInStore.setAddress(address);

                int locationIndex = cursorInStore.getColumnIndex(DatabaseHelper.LOCATION_ID);
                String location_id = cursorInStore.getString(locationIndex);
                resultInStore.setLocationId(location_id);

                int countryIndex = cursorInStore.getColumnIndex(DatabaseHelper.COUNTRY);
                String country = cursorInStore.getString(countryIndex);
                resultInStore.setCountry(country);

                int cityIndex = cursorInStore.getColumnIndex(DatabaseHelper.CITY);
                String city = cursorInStore.getString(cityIndex);
                resultInStore.setCity(city);

                int latitudeIndex = cursorInStore.getColumnIndex(DatabaseHelper.LATITUDE);
                String latitude = cursorInStore.getString(latitudeIndex);
                resultInStore.setLatitude(latitude);

                int longitudeIndex = cursorInStore.getColumnIndex(DatabaseHelper.LONGITUDE);
                String longitude = cursorInStore.getString(longitudeIndex);
                resultInStore.setLongitude(longitude);


                int brandIdIndex = cursorInStore.getColumnIndex(DatabaseHelper.BRAND_ID);
                String brand_id = cursorInStore.getString(brandIdIndex);
                resultInStore.setBrandId(brand_id);

                int brandNameIndex = cursorInStore.getColumnIndex(DatabaseHelper.BRAND_NAME);
                String brand_name = cursorInStore.getString(brandNameIndex);
                resultInStore.setBrandName(brand_name);

                int productIdIndex = cursorInStore.getColumnIndex(DatabaseHelper.PRODUCT_ID);
                String product_id = cursorInStore.getString(productIdIndex);
                resultInStore.setProductId(product_id);

                int productNameIndex = cursorInStore.getColumnIndex(DatabaseHelper.PRODUCT_NAME);
                String product_name = cursorInStore.getString(productNameIndex);
                resultInStore.setProductName(product_name);

                int upcIndex = cursorInStore.getColumnIndex(DatabaseHelper.UPC);
                String upc = cursorInStore.getString(upcIndex);
                resultInStore.setUpc(upc);

                int weightIndex = cursorInStore.getColumnIndex(DatabaseHelper.WEIGHT);
                String weight = cursorInStore.getString(weightIndex);
                resultInStore.setWeight(weight);

                int categoryNameIndex = cursorInStore.getColumnIndex(DatabaseHelper.CATEGORY_NAME);
                String category_name = cursorInStore.getString(categoryNameIndex);
                resultInStore.setCategoryName(category_name);

                int categoryIdIndex = cursorInStore.getColumnIndex(DatabaseHelper.CATEGORY_ID);
                String category_id = cursorInStore.getString(categoryIdIndex);
                resultInStore.setCategoryId(category_id);

                int rangeStartIndex = cursorInStore.getColumnIndex(DatabaseHelper.RANGE_START);
                String rangeStart = cursorInStore.getString(rangeStartIndex);
                resultInStore.setRangestart(rangeStart);

                int rangeEndIndex = cursorInStore.getColumnIndex(DatabaseHelper.RANGE_END);
                String rangeEnd = cursorInStore.getString(rangeEndIndex);
                resultInStore.setRangeend(rangeEnd);

                int rangeTypeIndex = cursorInStore.getColumnIndex(DatabaseHelper.RANGE_TYPE);
                String rangeType = cursorInStore.getString(rangeTypeIndex);
                resultInStore.setRangetype(rangeType);

                int imageIndex = cursorInStore.getColumnIndex(DatabaseHelper.IMAGE);
                byte[] image = cursorInStore.getBlob(imageIndex);
                resultInStore.setImageByte(image);

                int currencyIdIndex = cursorInStore.getColumnIndex(DatabaseHelper.CURRENCY_ID);
                String currencyId = cursorInStore.getString(currencyIdIndex);
                resultInStore.setCurrencyId(currencyId);

                int currencyNameIndex = cursorInStore.getColumnIndex(DatabaseHelper.CURRENCY_NAME);
                String currencyName = cursorInStore.getString(currencyNameIndex);
                resultInStore.setCurrencyName(currencyName);

                int dateAddedIndex = cursorInStore.getColumnIndex(DatabaseHelper.DATE_ADDED);
                String date_added = cursorInStore.getString(dateAddedIndex);
                resultInStore.setDateadded(date_added);

                int userIdIndex = cursorInStore.getColumnIndex(DatabaseHelper.USER_ID);
                String userId = cursorInStore.getString(userIdIndex);
                resultInStore.setUserId(userId);

                int firstNameIndex = cursorInStore.getColumnIndex(DatabaseHelper.FIRST_NAME);
                String firstName = cursorInStore.getString(firstNameIndex);
                resultInStore.setFirstName(firstName);

                int lastNameIndex = cursorInStore.getColumnIndex(DatabaseHelper.LAST_NAME);
                String lastName = cursorInStore.getString(lastNameIndex);
                resultInStore.setLastName(lastName);

                int profilePicIndex = cursorInStore.getColumnIndex(DatabaseHelper.PROFILE_PIC);
                String profilePic = cursorInStore.getString(profilePicIndex);
                resultInStore.setProfilePic(profilePic);

                int taxValueIndex = cursorInStore.getColumnIndex(DatabaseHelper.TAX_VALUE);
                String taxValue = cursorInStore.getString(taxValueIndex);
                resultInStore.setTaxValue(taxValue);


                int stockInMeasureIndex = cursorInStore.getColumnIndex(DatabaseHelper.STOCK_IN_MEASURE);
                String stockInMeasure = cursorInStore.getString(stockInMeasureIndex);
                resultInStore.setStockUnitMeasure(stockInMeasure);

                int itemInMeasureIndex = cursorInStore.getColumnIndex(DatabaseHelper.ITEM_IN_MEASURE);
                String itemInMeasure = cursorInStore.getString(itemInMeasureIndex);
                resultInStore.setItemUnitMeasure(itemInMeasure);

                int samplingDateIndex = cursorInStore.getColumnIndex(DatabaseHelper.SAMPLING_DATE);
                String samplingDate = cursorInStore.getString(samplingDateIndex);
                resultInStore.setSamplingDate(samplingDate);

                int commentIndex = cursorInStore.getColumnIndex(DatabaseHelper.COMMENT);
                String comment = cursorInStore.getString(commentIndex);
                resultInStore.setComment(comment);

                arrayListInStore.add(resultInStore);
            }
        }

        dashboardView.onSuccessOfInstoreSampling(arrayListInStore);
    }

    //<!------------------------------------Planogram Data----------------------------------------->

    public void getPlanogramDataDb() {


        ArrayList<DashboardPlanogramModel.Result> arrayListPlanogram = new ArrayList<>();
        DashboardPlanogramModel.Result resultPlanogram = new DashboardPlanogramModel().new Result();

        Cursor cursorPlanogram = databaseHelper.getDataFromTable(DatabaseHelper.TABLE_PLANOGRAM);

        if(cursorPlanogram!=null) {

            while (cursorPlanogram.moveToNext()) {
                int titleIndex = cursorPlanogram.getColumnIndex(DatabaseHelper.TITLE);
                String titleId = cursorPlanogram.getString(titleIndex);
                resultPlanogram.setTitle(titleId);

                int idIndex = cursorPlanogram.getColumnIndex(DatabaseHelper.PLANOGRAM_ID);
                String id = cursorPlanogram.getString(idIndex);
                resultPlanogram.setPlanogramId(id);

                int messageIndex = cursorPlanogram.getColumnIndex(DatabaseHelper.MESSAGE);
                String message = cursorPlanogram.getString(messageIndex);
                resultPlanogram.setMessage(message);

                int linkIndex = cursorPlanogram.getColumnIndex(DatabaseHelper.LINK);
                String link = cursorPlanogram.getString(linkIndex);
                resultPlanogram.setLink(link);

                int dateAddedIndex = cursorPlanogram.getColumnIndex(DatabaseHelper.DATE_ADDED);
                String dateAdded = cursorPlanogram.getString(dateAddedIndex);
                resultPlanogram.setDateadded(dateAdded);

                arrayListPlanogram.add(resultPlanogram);
            }
        }

        dashboardView.onSuccessPlanogram(arrayListPlanogram);

    }

   /* private byte[] getByteArrayImage(String url){
        try {
            URL imageUrl = new URL(url);
            URLConnection ucon = imageUrl.openConnection();

            InputStream is = ucon.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);

            ByteArrayBuffer baf = new ByteArrayBuffer(500);
            int current = 0;
            while ((current = bis.read()) != -1) {
                baf.append((byte) current);
            }

            return baf.toByteArray();
        } catch (Exception e) {
            Log.d("ImageManager", "Error: " + e.toString());
        }
        return null;
    }*/
}
