<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head>
    <link rel="stylesheet" href="${context}/bootstrap/css/bootstrap.css"/>
    <%--<link rel="stylesheet" href="${context}/neo/styles.css"/>--%>
    <script type="text/javascript" src="${context}/scripts/lib/jquery-1.9.1.js"></script>

    <script type="text/javascript" src="${context}/scripts/lib/knockout-2.2.1.js"></script>
    <script type="text/javascript" src="${context}/scripts/lib/knockout.validation.js"></script>
    <script type="text/javascript" src="${context}/scripts/lib/require.js"></script>
    <script type="text/javascript" src="${context}/bootstrap/js/bootstrap.js"></script>
    <script type="text/javascript" src="${context}/scripts/app.js"></script>
</head>
<body>

<div class="container">

    <div class="navbar navbar-fixed-top">
        <div class="navbar-inner">
            <div class="container">
                <a class="brand" href="#"><img src="${context}/images/logo.jpg" width="25px"> Pptx Generator</a>
            </div>
        </div>
    </div>

    <br/><br/><br/>

    <form:form commandName="jsonData" method="post" action="/get-new-pptx" data-bind="submit: hasNoErrors ">
        <form:input type="hidden" path="sprintJson"
                    data-bind="value: objectToJson()"></form:input>
        <button class="btn btn-primary" type="submit" data-bind="enable: stories().length > 0">
            <i class="icon-download-alt icon-white"></i>
            <strong>Generate pptx</strong></button>
    </form:form>

    <h4>Total stories(<span data-bind="text: stories().length"></span>)</h4>

    <div class="well form-inline">
        <div class="row">
            <div class="control-group span3" data-bind="css: { error: isFieldValid()}">
                <label class="control-label ">Project Name</label>

                <div class="controls">
                    <input type="text" data-bind="value: projectName"/>
                </div>

            </div>
            <div class="control-group span4 " data-bind="css: { error: !tranche.isValid()}">
                <label class="control-label">Tranche</label>

                <div class="controls">
                    <input type="text" data-bind="value: tranche"/>
                </div>
            </div>
            <div class="control-group span4" data-bind="css: { error: !title.isValid()}">
                <label class="control-label">Title</label>

                <div class="controls">
                    <input type="text" data-bind="value: title"/>
                </div>
            </div>

        </div>
        <div class="row">
            <div class="control-group span3">
                <label class="control-label">Projects</label>

                <div class="controls">
                    <select data-bind="
                 options:projects,
                 optionsText: 'name',
                 optionsValue: 'id',
                 value: projectId,
                 optionsCaption: 'Choose...',
                 event:{ change: projectSelected}"> </select>
                </div>
            </div>
            <div class="control-group span4" data-bind="visible: sprints().length > 0">
                <label class="control-label">Sprint</label>

                <div class="controls">
                    <select data-bind="
                 options:sprints,
                 optionsText: 'sprintName',
                 value: sprintData,
                 optionsCaption: 'Choose...',
                 event:{ change: sprintSelected}"> </select>
                </div>
            </div>


        </div>
    </div>


    <div class="alert alert-error" data-bind=" visible: displayErrors">
        <button type="button" class="close" data-dismiss="alert">&times;</button>
        <strong>Warning!</strong> For grouping slides by stakeholders, please provide <strong>Actor</strong> field
        for each story in <strong>Rally</strong>
    </div>


    <table class="table table-striped"
           data-bind="visible: stories().length > 0">
        <thead>
        <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Actor</th>
            <th>Presenter</th>
            <th>Is feature</th>
        </tr>
        </thead>

        <tbody data-bind="foreach: stories">
        <tr data-bind="css: { error: !actor.isValid()}">
            <td><a data-bind="text: code, attr: { href: $parent.storyLink($data)}" target="_blank"/></td>
            <td data-bind="text: name"></td>
            <td data-bind="text: actor"></td>
            <td><select data-bind="options: $root.teams, value: team"></select></td>
            <td><input type="checkbox" checked="true" data-bind="checked: feature"/></td>
        </tr>
        </tbody>
    </table>
</div>
</body>
</html>