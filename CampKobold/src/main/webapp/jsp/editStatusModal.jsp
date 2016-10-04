<%-- 
    Document   : editStatusModalFragment
    Created on : May 24, 2016, 8:24:48 PM
    Author     : Brandon
--%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<div class="modal fade" id="editStatusModal" tabindex="-1" role="dialog" aria-labelledby="editStatusModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">&times</span>
                    <span class="sr-only">Close</span>
                </button>
                <h4 class="modal-title">Equipment Tag #: <span id="edit-header-asset-record-asset-id"></span></h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-6">
                        <br>
                        <form class="form-horizontal" role="form">
                            <div class="form-group">
                                <label for="edit-asset-record-member" class="col-md-5 control-label">Member Id</label>
                                <div class="col-md-7">
                                    <input type="text" class="form-control" id="edit-asset-record-member">
                                </div>
                            </div>
                            <div class="col-md-12">
                                <div class="form-group">
                                    <label for="edit-asset-record-status"></label>
                                    <select class="form-control" id="edit-asset-record-status">
                                        <option value="" selected="selected">Select Status</option>
                                        <option value="1">AVAILABLE</option>
                                        <option value="2">CHECKED OUT</option>  
                                        <option value="3">BROKEN</option>
                                        <option value="4">LOST</option>
                                        <option value="5">UNDER REPAIRS</option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-md-12" id="assetNotAvailable" style="color: red">
                                Asset is not available
                            </div>
                            <div class="col-md-12" id="duplicate" style="color: red">
                                Duplicate Status Entered
                            </div>
                            <div class="col-md-12" id="memberEmpty" style="color: red">
                                You must enter a Member ID
                            </div>
                            <div class="col-md-12" id="memberDoesNotExist" style="color: red">
                                Member ID is invalid
                            </div>
                            <div class="col-md-12" id="validationErrors" style="color: red">
                            </div>
                    </div>
                    <div class="col-md-6">
                        <h4>Extra Notes:</h4>
                        <div class="form-group">
                            <textarea class="form-control" rows="4" cols="30" id="edit-asset-record-note"></textarea>
                        </div>
                        <div class="form-group">
                            <input type="hidden" id="edit-asset-record-asset-id" />
                            <input type="hidden" id="edit-asset-record-id" />
                            <button type="submit" class="btn btn-primary" id="edit-button">Submit</button>
                            <button type="submit" class="btn btn-info" data-dismiss="modal">Cancel</button>
                            <sec:authorize access="hasRole('ROLE_ADMIN')">
                                <button type="submit" class="btn btn-danger" id="delete-asset-record-button" data-dismiss="modal">Delete</button>
                            </sec:authorize>
                        </div>
                    </div>
                    </form>

                </div>
            </div>
        </div>
    </div>
</div>
