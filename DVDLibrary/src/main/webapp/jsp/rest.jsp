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
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>DVD Library</title>
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/dvdlogo1.png">
    </head>
    <body>
        <div class="container-fluid">
            <div class="content">
                <img src="${pageContext.request.contextPath}/img/dvdlogo1.png" 
                     alt="dvd_logo" 
                     style="padding-right: 5px" 
                     height="30" 
                     width="35" 
                     align="left">
                <h2>DVD Library</h2>
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
                             style="pointer-events: none; ">DVD Library</div>
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
                                <a href="${pageContext.request.contextPath}/displayDVDsNoAjax">No Ajax</a>
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
            <div class="row">
                <div class="col-md-6">
                    <div id="contactTableDiv">
                        <h3>My DVDs</h3>
                        <table id="contactTable" class="table table-hover">
                            <tr>
                                <th width="40%">Title</th>
                                <th width="30%">Release Date</th>
                                <th width="15%"></th>
                                <th width="15%"></th>
                            </tr>
                            <tbody id="contentRows"></tbody>
                        </table>
                    </div>
                </div> 
                <div class="col-md-6">
                    <div id="editFormDiv" style="display: none">
                        <h3 onclick="hideEditForm()">Edit DVD</h3>
                        <form class="form-horizontal" role="form">
                            <div class="form-group">
                                <label for="edit-title" class="col-md-4 control-label">
                                    Title:
                                </label>

                                <div class="col-md-8">
                                    <input type="text"
                                           class="form-control"
                                           id="edit-title"
                                           placeholder="Title"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="edit-release-date" class="col-md-4 control-label">
                                    Release Date:
                                </label>

                                <div class="col-md-8">
                                    <input type="text"
                                           class="form-control"
                                           id="edit-release-date"
                                           placeholder="Release Date"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="edit-mpaa-rating" class="col-md-4 control-label">
                                    MPAA Rating:</label>

                                <div class="col-md-8">
                                    <input type="text"
                                           class="form-control"
                                           id="edit-mpaa-rating"
                                           placeholder="MPAA Rating"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="edit-direcor" class="col-md-4 control-label">Director:</label>

                                <div class="col-md-8">
                                    <input type="text"
                                           class="form-control"
                                           id="edit-director"
                                           placeholder="Director"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="edit-studio" class="col-md-4 control-label">Studio:</label>

                                <div class="col-md-8">
                                    <input type="text"
                                           class="form-control"
                                           id="edit-studio"
                                           placeholder="Studio"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="edit-genre" class="col-md-4 control-label">Genre:</label>
                                <div class="col-md-8">
                                    <select id="edit-genre" class="form-control">
                                        <option value="" selected>Genre</option>
                                        <option value="1">ACTION</option>
                                        <option value="2">CLASSIC</option>
                                        <option value="3">COMEDY</option>
                                        <option value="4">DOCUMENTARY</option>
                                        <option value="5">DRAMA</option>
                                        <option value="6">ROMANCE</option>
                                        <option value="7">THRILLER</option>
                                        <option value="8">SCI-FI</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-md-offset-4 col-md-8">
                                    <input type="hidden" id="edit-dvd-id">
                                    <button type="submit"
                                            id="edit-button"
                                            class="btn btn-primary">
                                        Update DVD
                                    </button>
                                    <button type="button"
                                            id="edit-cancel-button"
                                            class="btn btn-danger"
                                            onclick="hideEditForm()">
                                        Cancel
                                    </button>
                                </div>
                            </div>
                        </form>
                        <div id="validationErrorsEdit" style="color: red" />
                    </div>
                </div>
                <div id="addFormDiv">
                    <h3>Add New DVD</h3>
                    <form class="form-horizontal" role="form">
                        <div class="form-group">
                            <label for="add-title" class="col-md-4 control-label">
                                Title:
                            </label>
                            <div class="col-md-8">
                                <input type="text"
                                       class="form-control"
                                       id="add-title"
                                       placeholder="Title"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="add-release-date" class="col-md-4 control-label">
                                Release Date:
                            </label>

                            <div class="col-md-8">
                                <input type="text"
                                       class="form-control"
                                       id="add-release-date"
                                       placeholder="Release Date"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="add-mpaa-rating" class="col-md-4 control-label">
                                MPAA Rating:</label>

                            <div class="col-md-8">
                                <input type="text"
                                       class="form-control"
                                       id="add-mpaa-rating"
                                       placeholder="MPAA Rating"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="add-director" class="col-md-4 control-label">Director:</label>

                            <div class="col-md-8">
                                <input type="text"
                                       class="form-control"
                                       id="add-director"
                                       placeholder="Director"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="add-studio" class="col-md-4 control-label">Studio:</label>

                            <div class="col-md-8">
                                <input type="text"
                                       class="form-control"
                                       id="add-studio"
                                       placeholder="Studio"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="add-genre" class="col-md-4 control-label">Genre:</label>
                            <div class="col-md-8">
                                <select id="add-genre" class="form-control">
                                    <option value="" selected>Genre</option>
                                    <option value="1">ACTION</option>
                                    <option value="2">CLASSIC</option>
                                    <option value="3">COMEDY</option>
                                    <option value="4">DOCUMENTARY</option>
                                    <option value="5">DRAMA</option>
                                    <option value="6">ROMANCE</option>
                                    <option value="7">THRILLER</option>
                                    <option value="8">SCI-FI</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-md-offset-4 col-md-8">
                                <button type="submit"
                                        id="add-button"
                                        class="btn btn-primary">
                                    Create DVD
                                </button>
                            </div>
                        </div>
                    </form>
                    <div id="validationErrors" style="color: red" />
                </div>
            </div> <!-- End col div -->
        </div> <!-- End row div -->
    </div>
</div>
<jsp:include page="footer.jsp"/>
<script src="js/jquery-1.11.3.min.js"></script>
<script src="js/bootstrap.js"></script>
<script src="js/restDVDLibrary.js"></script>
</body>
</html>