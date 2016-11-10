<%-- 
    Document   : rest
    Created on : Apr 4, 2016, 2:58:30 PM
    Author     : apprentice
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Address Book</title>
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/addressbook.png">
    </head>
    <body>
        <div class="container-fluid">
            <div class="content">
                <img src="${pageContext.request.contextPath}/img/addressbook.png" 
                     alt="addressbook_logo" 
                     style="padding-right: 5px" 
                     height="30" 
                     width="35" 
                     align="left">
                <h2>Address Book</h2>
            </div>
            <nav class="navbar navbar-default">
                <div class="container-fluid">
                    <div class="navbar-header">
                        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                            <span class="sr-only">Toggle navigation</span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                        </button>
                        <div class="navbar-brand" 
                             style="pointer-events: none; ">Address Book</div>
                    </div>

                    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                        <ul class="nav navbar-nav"> 
                            <li role="presentation">
                                <a href="${pageContext.request.contextPath}/home">Home</a>
                            </li>
                            <li role="presentation" class="active">
                                <a href="${pageContext.request.contextPath}/rest">REST</a>
                            </li>
                            <li role="presentation">
                                <a href="${pageContext.request.contextPath}/restmodal">REST (Modal)</a>
                            </li>
                            <li role="presentation">
                                <a href="${pageContext.request.contextPath}/displayAddressesNoAjax">No Ajax</a>
                            </li>
                            <li role="presentation">
                                <a href="${pageContext.request.contextPath}/search">Search</a>
                            </li>
                            <li role="presentation">
                                <a href="${pageContext.request.contextPath}/stats">Stats</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
        </div>
        <div class="container-fluid" style="margin: 0px 10px;">
            <!--
            Add a row to our container - this will hold the summary table and the new contact form.
            -->
            <div class="row">
                <!-- #2: Add a col to hold the summary table - have it take up half the row -->
                <div class="col-md-6">
                    <div id="contactTableDiv">
                        <h2>My Address Book</h2>

                        <table id="contactTable" class="table table-hover">
                            <tr>
                                <th width="40%">Contact Name</th>
                                <th width="30%">City</th>
                                <th width="15%"></th>
                                <th width="15%"></th>
                            </tr>
                            <!--
                             #3: This holds the list of contacts - we will add rows
                            dynamically
                             using jQuery
                            -->
                            <tbody id="contentRows"></tbody>
                        </table>
                    </div>
                </div> <!-- End col div -->
                <!--
                #4: Add col to hold the new contact form - have it take up the other half of the row
                -->
                <div class="col-md-6">

                    <div id="editFormDiv" style="display: none">
                        <h2 onclick="hideEditForm()">Edit Address</h2>

                        <form class="form-horizontal" role="form">
                            <div class="form-group">
                                <label for="edit-first-name" class="col-md-4 control-label">
                                    First Name:
                                </label>

                                <div class="col-md-8">
                                    <input type="text"
                                           class="form-control"
                                           id="edit-first-name"
                                           placeholder="First Name"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="edit-last-name" class="col-md-4 control-label">
                                    Last Name:
                                </label>

                                <div class="col-md-8">
                                    <input type="text"
                                           class="form-control"
                                           id="edit-last-name"
                                           placeholder="Last Name"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="edit-street" class="col-md-4 control-label">
                                    Street:</label>

                                <div class="col-md-8">
                                    <input type="text"
                                           class="form-control"
                                           id="edit-street"
                                           placeholder="Street"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="edit-city" class="col-md-4 control-label">City:</label>

                                <div class="col-md-8">
                                    <input type="text"
                                           class="form-control"
                                           id="edit-city"
                                           placeholder="City"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="edit-state" class="col-md-4 control-label">State:</label>

                                <div class="col-md-8">
                                    <input type="text"
                                           class="form-control"
                                           id="edit-state"
                                           placeholder="State"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="edit-zip" class="col-md-4 control-label">Zip:</label>

                                <div class="col-md-8">
                                    <input type="text"
                                           class="form-control"
                                           id="edit-zip"
                                           placeholder="Zip"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-md-offset-4 col-md-4">
                                    <input type="hidden" id="edit-address-id">

                                    <button type="button"
                                            id="edit-cancel-button"
                                            class="btn btn-danger"
                                            onclick="hideEditForm()">
                                        Cancel
                                    </button>
                                </div>
                                <div class="col-md-4">
                                    <button type="submit"
                                            id="edit-button"
                                            class="btn btn-primary">
                                        Update Address
                                    </button>
                                </div>
                            </div>

                        </form>
                        <div id="validationErrorsEdit" style="color: red" />
                    </div>
                </div>

                <div id="addFormDiv">

                    <h2>Add New Address</h2>

                    <form class="form-horizontal" role="form">
                        <div class="form-group">
                            <label for="add-first-name" class="col-md-4 control-label">
                                First Name:
                            </label>

                            <div class="col-md-8">
                                <input type="text"
                                       class="form-control"
                                       id="add-first-name"
                                       placeholder="First Name"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="add-last-name" class="col-md-4 control-label">
                                Last Name:
                            </label>

                            <div class="col-md-8">
                                <input type="text"
                                       class="form-control"
                                       id="add-last-name"
                                       placeholder="Last Name"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="add-street" class="col-md-4 control-label">
                                Street:</label>

                            <div class="col-md-8">
                                <input type="text"
                                       class="form-control"
                                       id="add-street"
                                       placeholder="Street"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="add-city" class="col-md-4 control-label">City:</label>

                            <div class="col-md-8">
                                <input type="text"
                                       class="form-control"
                                       id="add-city"
                                       placeholder="City"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="add-state" class="col-md-4 control-label">State:</label>

                            <div class="col-md-8">
                                <input type="text"
                                       class="form-control"
                                       id="add-state"
                                       placeholder="State"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="add-zip" class="col-md-4 control-label">Zip:</label>

                            <div class="col-md-8">
                                <input type="text"
                                       class="form-control"
                                       id="add-zip"
                                       placeholder="Zip"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-md-offset-4 col-md-8">
                                <button type="submit"
                                        id="add-button"
                                        class="btn btn-primary">
                                    Create Address
                                </button>
                            </div>
                        </div>
                    </form>
                    <div id="validationErrors" style="color: red" />
                </div>
            </div>
        </div> <!-- End col div -->
    </div> <!-- End row div -->
    <jsp:include page="footer.jsp"/>
</div>
<!-- #5: Placed at the end of the document so the pages load faster -->
<script src="js/jquery-1.11.3.min.js"></script>
<script src="js/bootstrap.js"></script>
<script src="js/rest.js"></script>
</body>
</html>